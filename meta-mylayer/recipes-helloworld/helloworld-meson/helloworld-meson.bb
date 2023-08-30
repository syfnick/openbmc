# build helloworld application by meson
SUMMARY = "helloworld of meson recipe"
DESCRIPTION = "My hello world application"
LICENSE = "CLOSED"

inherit meson

FILESEXTRAPATHS:prepend := "${THISDIR}/:"
SRC_URI = "file://helloworld/"

S = "${WORKDIR}/helloworld"

FILES:${PN} = "${bindir}/"
