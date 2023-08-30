FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS += "gpioplus"
DEPENDS += "systemd"

EXTRA_OEMESON:append = " -Dhost-gpios=enabled"

SRC_URI += " \
    file://host-control \
    file://chassis-control \
    file://ncplite-chassis-poweroff@.service \
    file://ncplite-chassis-poweron@.service \
    file://ncplite-host-off@.service \
    file://ncplite-host-force-reset@.service \
    "

RDEPENDS:${PN}:append = " bash"

DBUS_PACKAGES:append = "${PN}-ncplite"
PACKAGE_BEFORE_PN += "${PN}-ncplite"
SYSTEMD_PACKAGES += "${PN}-ncplite"

SYSTEMD_SERVICE:${PN}-ncplite += "ncplite-chassis-poweron@.service"
SYSTEMD_SERVICE:${PN}-ncplite += "ncplite-chassis-poweroff@.service"
SYSTEMD_SERVICE:${PN}-ncplite += "ncplite-host-off@.service"
SYSTEMD_SERVICE:${PN}-ncplite += "ncplite-host-force-reset@.service"

# Chassis power on
CHASSIS_POWERON_SVC = "ncplite-chassis-poweron@.service"
CHASSIS_POWERON_INSTMPL = "ncplite-chassis-poweron@{0}.service"
CHASSIS_POWERON_TGTFMT = "obmc-chassis-poweron@{0}.target"
CHASSIS_POWERON_FMT = "../${CHASSIS_POWERON_SVC}:${CHASSIS_POWERON_TGTFMT}.requires/${CHASSIS_POWERON_INSTMPL}"
SYSTEMD_LINK:${PN}-ncplite += "${@compose_list_zip(d, 'CHASSIS_POWERON_FMT', 'OBMC_CHASSIS_INSTANCES')}"

# Chassis power off
CHASSIS_POWEROFF_SVC = "ncplite-chassis-poweroff@.service"
CHASSIS_POWEROFF_INSTMPL = "ncplite-chassis-poweroff@{0}.service"
CHASSIS_POWEROFF_TGTFMT = "obmc-chassis-poweroff@{0}.target"
CHASSIS_POWEROFF_FMT = "../${CHASSIS_POWEROFF_SVC}:${CHASSIS_POWEROFF_TGTFMT}.requires/${CHASSIS_POWEROFF_INSTMPL}"
SYSTEMD_LINK:${PN}-ncplite += "${@compose_list_zip(d, 'CHASSIS_POWEROFF_FMT', 'OBMC_CHASSIS_INSTANCES')}"

# Host off
HOST_OFF_SVC = "ncplite-host-off@.service"
HOST_OFF_INSTMPL = "ncplite-host-off@{0}.service"
HOST_OFF_TGTFMT = "obmc-host-shutdown@{0}.target"
HOST_OFF_FMT = "../${HOST_OFF_SVC}:${HOST_OFF_TGTFMT}.requires/${HOST_OFF_INSTMPL}"
SYSTEMD_LINK:${PN}-ncplite += "${@compose_list_zip(d, 'HOST_OFF_FMT', 'OBMC_HOST_INSTANCES')}"

# Host force reboot
HOST_FORCE_RESET_SVC = "ncplite-host-force-reset@.service"
HOST_FORCE_RESET_INSTMPL = "ncplite-host-force-reset@{0}.service"
HOST_FORCE_RESET_TGTFMT = "obmc-host-force-warm-reboot@{0}.target"
HOST_FORCE_RESET_TARGET_FMT = "../${HOST_FORCE_RESET_SVC}:${HOST_FORCE_RESET_TGTFMT}.requires/${HOST_FORCE_RESET_INSTMPL}"
SYSTEMD_LINK:${PN}-ncplite += "${@compose_list_zip(d, 'HOST_FORCE_RESET_TARGET_FMT', 'OBMC_HOST_INSTANCES')}"

# Chassis hard power off require host off in our machine
CHASSIS_HARD_POWEROFF_TGTFMT = "obmc-chassis-hard-poweroff@{0}.target"
CHASSIS_HOST_OFF_FMT = "../${HOST_OFF_SVC}:${CHASSIS_HARD_POWEROFF_TGTFMT}.requires/${HOST_OFF_INSTMPL}"
SYSTEMD_LINK:${PN}-ncplite += "${@compose_list_zip(d, 'CHASSIS_HOST_OFF_FMT', 'OBMC_CHASSIS_INSTANCES')}"

do_install:append() {
    install -d ${D}${sbindir}
    install -m 0744 ${WORKDIR}/host-control ${D}${sbindir}/
    install -m 0744 ${WORKDIR}/chassis-control ${D}${sbindir}/
}

FILES:${PN} += "${systemd_system_unitdir}/*"
FILES:${PN}-host += "${bindir}/phosphor-host-condition-gpio"
SYSTEMD_SERVICE:${PN}-host += "phosphor-host-condition-gpio@.service"

pkg_postinst:${PN}-obmc-targets:prepend() {
    mkdir -p $D$systemd_system_unitdir/multi-user.target.requires
    LINK="$D$systemd_system_unitdir/multi-user.target.requires/phosphor-host-condition-gpio@0.service"
    TARGET="../phosphor-host-condition-gpio@.service"
    ln -s $TARGET $LINK
}

pkg_prerm:${PN}-obmc-targets:prepend() {
    LINK="$D$systemd_system_unitdir/multi-user.target.requires/phosphor-host-condition-gpio@0.service"
    rm $LINK
}
