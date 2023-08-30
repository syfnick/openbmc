
SUMMARY = "Kexec fast reboot tools"
DESCRIPTION = "Kexec is a fast reboot feature that lets you reboot to a new Linux kernel"
HOMEPAGE = "http://kernel.org/pub/linux/utils/kernel/kexec/"
SECTION = "kernel/userland"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=ea5bed2f60d357618ca161ad539f7c0a \
                    file://kexec/kexec.c;beginline=1;endline=20;md5=af10f6ae4a8715965e648aa687ad3e09"
DEPENDS = "zlib xz"

SRC_URI = "${KERNELORG_MIRROR}/linux/utils/kernel/kexec/kexec-tools-${PV}.tar.gz \
           file://kdump \
           file://kdump.conf \
           file://kdump.service \
           file://0001-powerpc-change-the-memory-size-limit.patch \
           file://0002-purgatory-Pass-r-directly-to-linker.patch \
           file://0003-kexec-ARM-Fix-add_buffer_phys_virt-align-issue.patch \
           file://0005-Disable-PIE-during-link.patch \
           file://0001-arm64-kexec-disabled-check-if-kaslr-seed-dtb-propert.patch \
           "

SRC_URI[sha256sum] = "89bdd941542c64fec16311858df304ed3a3908c1a60874d69df5d9bf1611e062"

inherit autotools update-rc.d systemd

export LDFLAGS = "-L${STAGING_LIBDIR}"
EXTRA_OECONF = " --with-zlib=yes"

do_compile:prepend() {
    # Remove the prepackaged config.h from the source tree as it overrides
    # the same file generated by configure and placed in the build tree
    rm -f ${S}/include/config.h

    # Remove the '*.d' file to make sure the recompile is OK
    for dep in `find ${B} -type f -name '*.d'`; do
        dep_no_d="`echo $dep | sed 's#.d$##'`"
        # Remove file.d when there is a file.o
        if [ -f "$dep_no_d.o" ]; then
            rm -f $dep
        fi
    done
}

do_install:append () {
        install -d ${D}${sysconfdir}/sysconfig
        install -m 0644 ${WORKDIR}/kdump.conf ${D}${sysconfdir}/sysconfig

        if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
                install -D -m 0755 ${WORKDIR}/kdump ${D}${sysconfdir}/init.d/kdump
        fi

        if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
                install -D -m 0755 ${WORKDIR}/kdump ${D}${libexecdir}/kdump-helper
                install -D -m 0644 ${WORKDIR}/kdump.service ${D}${systemd_system_unitdir}/kdump.service
                sed -i -e 's,@LIBEXECDIR@,${libexecdir},g' ${D}${systemd_system_unitdir}/kdump.service
        fi
}

PACKAGES =+ "kexec kdump vmcore-dmesg"

ALLOW_EMPTY:${PN} = "1"
RRECOMMENDS:${PN} = "kexec kdump vmcore-dmesg"

FILES:kexec = "${sbindir}/kexec"
FILES:kdump = "${sbindir}/kdump \
               ${sysconfdir}/sysconfig/kdump.conf \
               ${sysconfdir}/init.d/kdump \
               ${libexecdir}/kdump-helper \
               ${systemd_system_unitdir}/kdump.service \
"

FILES:vmcore-dmesg = "${sbindir}/vmcore-dmesg"

INITSCRIPT_PACKAGES = "kdump"
INITSCRIPT_NAME:kdump = "kdump"
INITSCRIPT_PARAMS:kdump = "start 56 2 3 4 5 . stop 56 0 1 6 ."

SYSTEMD_PACKAGES = "kdump"
SYSTEMD_SERVICE:kdump = "kdump.service"

SECURITY_PIE_CFLAGS:remove = "-fPIE -pie"

COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*|powerpc.*|mips.*)-(linux|freebsd.*)'

INSANE_SKIP:${PN} = "arch"
