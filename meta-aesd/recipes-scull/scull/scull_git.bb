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
           file://0001-Updated-Makefile.patch \
           file://scull-init \
           file://0001-Replacing-insmod-with-modprobe.patch \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "a36b36b112ca1940036cd1216ac5a18246fea977"

S = "${WORKDIR}/git"

inherit module
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull-init"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
RPROVIDES:${PN} += "kernel-module-scull"

FILES:${PN} += "${bindir}/scull_load"
FILES:${PN} += "${bindir}/scull_unload"
FILES:${PN} += "${sysconfdir}/*"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/scull-init ${D}${sysconfdir}/init.d/scull-init

    install -d ${D}${bindir}
	install -m 0755 ${S}/scull/scull_load ${D}${bindir}/scull_load
    install -m 0755 ${S}/scull/scull_unload ${D}${bindir}/scull_unload
    
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/scull
    install -m 0644 ${S}/scull/scull.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/scull
}