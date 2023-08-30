SUMMARY = "PECI PCIe"
DESCRIPTION = "Gathers PCIe information using PECI \
and provides it on D-Bus"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7becf906c8f8d03c237bad13bc3dac53"
DEPENDS = "boost sdbusplus libpeci"
SRCREV = "3570b9ea5ff109a6e000d16a2d34b974fcf6c335"
PV = "0.1+git${SRCPV}"

PACKAGECONFIG ??= ""
PACKAGECONFIG[wait-for-os-standby] = "-Dwait-for-os-standby=enabled,-Dwait-for-os-standby=disabled"
PACKAGECONFIG[use-rdendpointcfg] = "-Duse-rdendpointcfg=enabled,-Duse-rdendpointcfg=disabled"

SRC_URI = "git://github.com/openbmc/peci-pcie;branch=master;protocol=https"

S = "${WORKDIR}/git"
SYSTEMD_SERVICE:${PN} += "xyz.openbmc_project.PCIe.service"

inherit meson pkgconfig systemd

