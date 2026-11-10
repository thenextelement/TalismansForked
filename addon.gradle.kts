tasks.withType<JavaExec>().configureEach {
    if (name.startsWith("runServer")) {
        doFirst("stripClientOnlyMods") {
            // Declared inside the action so the configuration cache can serialize it
            val clientOnlyModIds = listOf("angelica", "ModularUI2")
            classpath = classpath.filter { file ->
                clientOnlyModIds.none { file.name.contains(it, ignoreCase = true) }
            }
        }
    }
}
