project(helloyocto C)
cmake_minimum_required(VERSION 2.6.3)

add_executable (helloyocto main.c)

install(TARGETS helloyocto DESTINATION ${CMAKE_INSTALL_PREFIX}/bin)
