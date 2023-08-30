# helloworld application
SUMMARY = "helloworld of CMake recipe"
DESCRIPTION = "My hello world application"
LICENSE = "CLOSED"

inherit cmake

FILESEXTRAPATHS:prepend := "${THISDIR}/:"
SRC_URI = "file://helloworld/"

S = "${WORKDIR}/helloworld"

FILES:${PN} = "${bindir}/"
