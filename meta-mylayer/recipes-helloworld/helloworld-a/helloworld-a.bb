# helloworld application
SUMMARY = "helloworld of Autotools recipe"
DESCRIPTION = "My hello world application"
LICENSE = "CLOSED"

inherit autotools

FILESEXTRAPATHS:prepend := "${THISDIR}/:"
SRC_URI = "file://helloworld/"

S = "${WORKDIR}/helloworld"

FILES:${PN} = "${bindir}/"
