SUMMARY = "Simple helloworld application"
SECTION = "examples"
LICENSE = "CLOSED"

SRC_URI = "file://helloworld.c"

S = "${WORKDIR}"

B = "${S}"

do_compile () {
        ${CC} ${CFLAGS} ${LDFLAGS} helloworld.c -o helloworldo
}

do_install () {
        install -d ${D}${bindir}
        install -m 0755 helloworldo ${D}${bindir}
}

FILES:${PN} = "${bindir}"
