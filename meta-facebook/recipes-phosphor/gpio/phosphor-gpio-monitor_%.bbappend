FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:fb-compute-multihost = " file://phosphor-multi-gpio-monitor.json"
SRC_URI:append:fb-compute-multihost = " file://obmc-ipmb-rescan-fru.service"
SRC_URI:append:fb-compute-multihost = " file://ipmb-rescan-fru.sh"

RDEPENDS:${PN} += "bash"

SYSTEMD_SERVICE:${PN}-monitor += "phosphor-multi-gpio-monitor.service"
SYSTEMD_SERVICE:${PN} += "obmc-ipmb-rescan-fru.service"

SYSTEMD_LINK:${PN}-monitor:append = " ../phosphor-multi-gpio-monitor.service:multi-user.target.requires/phosphor-multi-gpio-monitor.service"

GPIO_HOST_TEMPLATES:append = " ${WORKDIR}/phosphor-multi-gpio-monitor.json"

do_install:append:fb-compute-multihost() {

    install -d ${D}${datadir}/phosphor-gpio-monitor
    install -m 0644 ${WORKDIR}/phosphor-multi-gpio-monitor.json \
                    ${D}${datadir}/phosphor-gpio-monitor/phosphor-multi-gpio-monitor.json

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/obmc-ipmb-rescan-fru.service \
                    ${D}${systemd_system_unitdir}

    install -d ${D}${libexecdir}/phosphor-gpio-monitor
    install -m 0777 ${WORKDIR}/ipmb-rescan-fru.sh ${D}${libexecdir}/phosphor-gpio-monitor/
}
FILES:${PN} += "${systemd_system_unitdir}/obmc-ipmb-rescan-fru.service"
