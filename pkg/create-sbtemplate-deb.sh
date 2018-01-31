#!/usr/bin/env bash
PACKAGE=sbtemplate
PACKAGE_ROOT="./sbtemplate-pkg"
VERSION=$GO_PIPELINE_LABEL
ARCH=all
DEPLOYMENT_ENV=$1

echo "Creating temp packaging directory ${PACKAGE_ROOT} ..."
mkdir -p $PACKAGE_ROOT
mkdir -p $PACKAGE_ROOT/DEBIAN
mkdir -p $PACKAGE_ROOT/var/lib/$PACKAGE
mkdir -p $PACKAGE_ROOT/etc/$PACKAGE
mkdir -p $PACKAGE_ROOT/etc/init.d
mkdir -p $PACKAGE_ROOT/etc/cron.d
mkdir -p $PACKAGE_ROOT/etc/logrotate.d
mkdir -p $PACKAGE_ROOT/tmp/$PACKAGE
mkdir -p $PACKAGE_ROOT/var/log/$COMPANY/$PACKAGE
mkdir -p $PACKAGE_ROOT/etc/default/

echo "creating company-profile file"
echo $DEPLOYMENT_ENV > $PACKAGE_ROOT/etc/$PACKAGE/company-profile

echo "Copying debian files to ${PACKAGE_ROOT} ..."
cp pkg/deb/$PACKAGE.control $PACKAGE_ROOT/DEBIAN/control
cp pkg/deb/$PACKAGE.postinst $PACKAGE_ROOT/DEBIAN/postinst
cp pkg/deb/$PACKAGE.postrm $PACKAGE_ROOT/DEBIAN/postrm
cp pkg/deb/$PACKAGE.preinst $PACKAGE_ROOT/DEBIAN/preinst
cp pkg/deb/$PACKAGE.prerm $PACKAGE_ROOT/DEBIAN/prerm
cp pkg/deb/$PACKAGE-init $PACKAGE_ROOT/etc/init.d/$PACKAGE
cp pkg/deploy/etc/logrotate.d/sbtemplate-access $PACKAGE_ROOT/etc/logrotate.d/sbtemplate-access
cp pkg/deploy/etc/cron.d/* $PACKAGE_ROOT/etc/cron.d/

echo "Updating version in control file ..."
sed -e "s/VERSION/${VERSION}\n/" -i $PACKAGE_ROOT/DEBIAN/control

echo "Copying files to ${PACKAGE_ROOT} ..."
cp -R sbtemplate/build/resources/main/public $PACKAGE_ROOT/var/lib/$PACKAGE/
cp sbtemplate/build/libs/sbtemplate-*-SNAPSHOT.jar $PACKAGE_ROOT/var/lib/$PACKAGE/sbtemplate-shaded.jar

ls $PACKAGE_ROOT/var/lib/$PACKAGE/

chmod -R 777 $PACKAGE_ROOT/var/lib/$PACKAGE/
chmod -R 777 $PACKAGE_ROOT/var/log/company/$PACKAGE

chmod 775 $PACKAGE_ROOT/DEBIAN/control
chmod 775 $PACKAGE_ROOT/DEBIAN/preinst
chmod 775 $PACKAGE_ROOT/DEBIAN/prerm
chmod 775 $PACKAGE_ROOT/DEBIAN/postinst
chmod 775 $PACKAGE_ROOT/DEBIAN/postrm

echo "Building debian ... dpkg-deb -b ${PACKAGE_ROOT}"
dpkg-deb -b $PACKAGE_ROOT

echo "Renaming debian ... mv $PACKAGE_ROOT.deb pkg/${PACKAGE}_${VERSION}_${ARCH}.deb"
mv $PACKAGE_ROOT.deb pkg/${PACKAGE}_${VERSION}_${ARCH}.deb

echo "Removing temp directory ${PACKAGE_ROOT} ..."
rm -r $PACKAGE_ROOT

echo "______________________________ ***   Done   *** ______________________________"
