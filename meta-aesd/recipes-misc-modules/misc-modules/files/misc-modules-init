#! /bin/sh

case "$1" in
    start)
        echo "Starting Misc Modules"
        /usr/bin/module_load faulty
        modprobe hello
        ;;
    stop)
        echo "Stopping Misc Modules"
        /usr/bin/module_unload
        rmmod hello
        ;;
    *)
        echo "Usage: $0 [start|stop]"
    exit 1
esac
exit 0