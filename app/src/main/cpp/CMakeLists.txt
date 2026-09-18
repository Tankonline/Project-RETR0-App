cmake_minimum_required(VERSION 3.22.1)

project("sonic-extractor")

# High-performance flags for the extraction engine
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O3 -ffast-math -flto")

add_library(
        sonic-extractor
        SHARED
        sonic-extractor.cpp
)

# Mandatory 16 KB page size support for Android 15+
target_link_options(sonic-extractor PRIVATE "-Wl,-z,max-page-size=16384")

find_library(
        log-lib
        log
)

# We use zlib for zip handling as it is built-in to the NDK.
find_library(
        z-lib
        z
)

target_link_libraries(
        sonic-extractor
        ${z-lib}
        ${log-lib}
)
