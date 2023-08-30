SUMMARY = "Phosphor OpenBMC BT to DBUS"
DESCRIPTION = "Phosphor OpenBMC BT to DBUS."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"
DEPENDS += "autoconf-archive-native"
DEPENDS += "systemd"
PROVIDES += "virtual/obmc-host-ipmi-hw"
SRCREV = "4aa837395933e7072a935e3aa4f14c86f5551cef"
PV = "1.0+git${SRCPV}"
PR = "r1"

SRC_URI = "git://github.com/openbmc/btbridge;branch=master;protocol=https"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
inherit obmc-phosphor-dbus-service

RRECOMMENDS:${PN} += "phosphor-ipmi-host"

RPROVIDES:${PN} += "virtual-obmc-host-ipmi-hw"

DBUS_SERVICE:${PN} = "org.openbmc.HostIpmi.service"
