import java.io.File


const val rootPath = "/Users/Anvith/Development/"
const val destPath = "${rootPath}humblerookie.github.io"
const val srcPath = "${rootPath}website/public"
const val srcCodePath = "${rootPath}website/"

fun copyFiles() {
    val target = File(destPath)
    File(srcPath).listFiles().forEach {
        it.copyRecursively(File(target.path, it.name), overwrite = true, onError = { file, ioException ->
            println("$ioException for file $file")
            OnErrorAction.SKIP
        })
    }
}

fun executeGitCommitForSource(message: String) {
    Runtime.getRuntime()
        .exec(
            arrayOf(
                "/bin/sh", "-c",
                "cd $srcCodePath &&" +
                        " git add . &&" +
                        " git commit -m \"$message\" &&" +
                        " git push origin master ;"

            )
        ).waitFor()
}

fun executeGitCommitForWebsite(message: String) {
    Runtime.getRuntime()
        .exec(
            arrayOf(
                "/bin/sh", "-c",
                " cd $destPath &&" +
                        " git add . &&" +
                        " git commit -m \"$message\" &&" +
                        " git push origin master;"

            )
        ).waitFor()
}

fun buildStaticFiles() {
    Runtime.getRuntime()
        .exec(
            arrayOf(
                "/bin/sh", "-c",
                "cd $srcCodePath &&" +
                        " gatsby build;"

            )
        ).waitFor()
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("You've not passed the git commit message")
    }
    val message = args.joinToString(" ")
    buildStaticFiles()
    copyFiles()
    executeGitCommitForSource(message)
    executeGitCommitForWebsite(message)
}
