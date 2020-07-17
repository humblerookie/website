import java.io.File


const val destPath = "/Users/Anvith/Development/humblerookie.github.io"
const val srcPath = "/Users/Anvith/Development/website/public"
fun copyFiles() {
    val target = File(destPath)
    File(srcPath).listFiles().forEach {
        it.copyRecursively(File(target.path, it.name), overwrite = true, onError = { file, ioException ->
            println("$ioException for file $file")
            OnErrorAction.SKIP
        })
    }
}

fun executeGitCommit(message: String) {
    Runtime.getRuntime().exec(arrayOf("/bin/sh", "-c", "cd $destPath; git add .;git commit -m \"$message\";git push origin master;"))
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("You've not passed the git commit message")
    }
    copyFiles()
    executeGitCommit(args[0])
}
