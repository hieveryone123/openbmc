SUMMARY = "Data recovery tool"
DESCRIPTION = "GNU ddrescue is a data recovery tool. It copies data \
    from one file or block device (hard disc, cdrom, etc) to another, \
    trying hard to rescue data in case of read errors."
HOMEPAGE = "http://www.gnu.org/software/ddrescue/ddrescue.html"
SECTION = "console"
LICENSE = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://COPYING;md5=cca7f74ec83b7a9ce7ccd195aad471bd \
                    file://main_common.cc;beginline=5;endline=16;md5=ad099df052bdd8297f490712285069da \
                    "

SRC_URI = "${GNU_MIRROR}/${BPN}/${BP}.tar.lz"
SRC_URI[sha256sum] = "01a414327853b39fba2fd0ece30f7bee2e9d8c8e8eb314318524adf5a60039a3"

# This isn't already added by base.bbclass
do_unpack[depends] += "lzip-native:do_populate_sysroot"

CONFIGUREOPTS = "\
    '--srcdir=${S}' \
    '--prefix=${prefix}' \
    '--exec-prefix=${exec_prefix}' \
    '--bindir=${bindir}' \
    '--datadir=${datadir}' \
    '--infodir=${infodir}' \
    '--sysconfdir=${sysconfdir}' \
    'CXX=${CXX}' \
    'CPPFLAGS=${CPPFLAGS}' \
    'CXXFLAGS=${CXXFLAGS}' \
    'LDFLAGS=${LDFLAGS}' \
"
EXTRA_OEMAKE = ""

do_configure () {
    ${S}/configure ${CONFIGUREOPTS}
}

do_install () {
    oe_runmake 'DESTDIR=${D}' install
    # Info dir listing isn't interesting at this point so remove it if it exists.
    if [ -e "${D}${infodir}/dir" ]; then
        rm -f ${D}${infodir}/dir
    fi
}
