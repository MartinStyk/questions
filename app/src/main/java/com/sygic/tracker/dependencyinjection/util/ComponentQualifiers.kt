package com.sygic.tracker.dependencyinjection.util

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ForApplication

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ForActivity
