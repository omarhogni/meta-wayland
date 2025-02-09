
SUMMARY = "wlroots based Phone compositor as used on the Librem5."
HOMEPAGE = "https://gitlab.gnome.org/World/Phosh/phoc"
SECTION = "graphics"
LICENSE = "GPL-3.0-only"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

REQUIRED_DISTRO_FEATURES = "wayland"

DEPENDS += " \
	gi-docgen-native \
	glib-2.0 \
	gmobile \
	gnome-desktop \
	libinput \
	libxkbcommon \
	pixman \
	seatd \
	wayland \
	wayland-native \
	wayland-protocols \
	wlroots-0.16 \
"

RDEPENDS:${PN} = "mutter"

SRC_URI = " \
	git://gitlab.gnome.org/World/Phosh/phoc.git;protocol=https;nobranch=1 \
	file://0001-meson.build-dont-build-subprojects.patch \
"

S = "${WORKDIR}/git"
PV = "0.30.0"
SRCREV = "0b14b8837a37977b3252ce957802469491f58ee2"

inherit features_check gsettings meson pkgconfig

PACKAGECONFIG[systemd] = ",,systemd"
PACKAGECONFIG[sysvinit] = ",,eudev elogind"
PACKAGECONFIG[x11] = ",,libxcb xcb-util-renderutil"
PACKAGECONFIG[xwayland] = "-Dxwayland=enabled,-Dxwayland=disabled,xwayland xcb-util-wm"

PACKAGECONFIG ?= " \
	${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)} \
	${@bb.utils.filter('DISTRO_FEATURES', 'sysvinit', d)} \
"

EXTRA_OEMESON += "-Dembed-wlroots=disabled --buildtype=release"

FILES:${PN} += "${datadir}"

