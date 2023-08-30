# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a64b1a3345125a5df8bd19a878582631"

SRC_URI = "git://git@github.com/syfnick/learn-yocto-devtools-modify.git;protocol=ssh;branch=master \
           file://0001-add-Modified-learn-yocto-devtools-modify-by-devtool-.patch \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "92e1012215a417138c16fbd5822998f9cab42640"

S = "${WORKDIR}/git"

inherit cmake

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = ""

