package com.retr0.projectretr0

import android.app.Application

/**
 * Explicit application entry point for both the normal app process and Shizuku's
 * UserService process. Some Android 15 OEM frameworks fail while creating a
 * UserService when the package has no explicit Application class.
 *
 * Keep this class side-effect free: the UserService process must be able to
 * construct it before any UI, database, or native initialization runs.
 */
class Retr0Application : Application()
