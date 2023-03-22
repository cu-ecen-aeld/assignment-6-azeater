# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-azeater;protocol=https;branch=master \
           file://0001-Updated-makefile-and-module_load.patch \
           file://misc-modules-init \
           file://0001-added-kernel-variable.patch \
           file://0002-updated-path.patch \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "a36b36b112ca1940036cd1216ac5a18246fea977"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-init"

RPROVIDES:${PN} += "kernel-module-misc-modules"

FILES:${PN} += "${bindir}/module_load"
FILES:${PN} += "${bindir}/module_unload"
FILES:${PN} += "${sysconfdir}/*"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/misc-modules-init ${D}${sysconfdir}/init.d/misc-modules-init

    install -d ${D}${bindir}
    install -m 0755 ${S}/misc-modules/module_load ${D}${bindir}/module_load
	install -m 0755 ${S}/misc-modules/module_unload ${D}${bindir}/module_unload

    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/hello
    install -m 0644 ${S}/misc-modules/hello.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/hello
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/faulty
    install -m 0644 ${S}/misc-modules/faulty.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/faulty
}