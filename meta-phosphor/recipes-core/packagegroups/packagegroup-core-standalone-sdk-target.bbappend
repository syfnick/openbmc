RRECOMMENDS:${PN}:append = " \
    cli11-dev \
    function2-dev \
    googletest \
    libcereal-dev \
    libstdc++-staticdev \
    nlohmann-json-dev \
    phosphor-dbus-interfaces-yaml \
    phosphor-logging \
    sdbusplus \
    "
RRECOMMENDS:${PN}:append:df-etcd = " etc-cpp-apiv3 protobuf"
