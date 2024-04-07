// other configs
ext {
    skipDownload = project.hasProperty('skipDownload') ? project.getProperty('skipDownload') as boolean : false
}    
repositories {
    if (skipDownload) {
        flatDir {
            dirs 'libs'
        }
    } else {
        mavenCentral()
    }
}
dependencies {
    if (skipDownload) {
        implementation files('libs/spring-boot-starter-data-jdbc-2.3.7.RELEASE.jar')

    } else {
        implementation group: 'org.springframework.boot', name: 'spring-boot-starter-data-jdbc', version: '2.3.7.RELEASE'

    }
}
task getDeps(type: Copy) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from configurations.compileClasspath into "libs/"
    from configurations.runtimeClasspath into "libs/"
}