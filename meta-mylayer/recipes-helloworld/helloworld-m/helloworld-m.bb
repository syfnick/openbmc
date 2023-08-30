SUMMARY = "helloworld of makefile recipe"
DESCRIPTION = "My hello world application"
LICENSE = "CLOSED"

FILESEXTRAPATHS:prepend := "${THISDIR}/:"
SRC_URI = "file://helloworld/"

S = "${WORKDIR}/helloworld"

CFLAGS:append = "-Wall -O -g"

EXTRA_OEMAKE = "'CC=${CC}' 'CFLAGS=${CFLAGS}'"
EXTRA_OEMAKE:append = " 'LDFLAGS=${LDFLAGS}'"
EXTRA_OEMAKE:append = " 'DESTDIR=${D}'"

do_install () {
  oe_runmake install
}

FILES:${PN} = "${bindir}/"
