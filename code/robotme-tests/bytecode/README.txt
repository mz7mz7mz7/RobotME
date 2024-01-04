In the subdirectories are located compiled java codes and their expected robotized java source codes
ASMified by org.objectweb.asm.util.ASMifierClassVisitor class.

Meaning of the subdirectories is as follows:
1) record subdirectory:
- *.class - compiled java bytecode with orygnal class to robotize (this file must be under version control system)
- *.class-record - compiled java bytecode with robotized above code (this file must NOT be under version control system)
- *.java - expected robotized and ASMified java source code (this file must be under version control system)
- *.class-record.java - actual robotized and ASMified java source code (this file must NOT be under version control system)
If corresponding *.java and *.class-record.java files hasn't any differences test are passed, otherwise it fails.

2) replay subdirectory:
- *.class - compiled java bytecode with orygnal class to robotize (this file must be under version control system)
- *.class-replay - compiled java bytecode with robotized above code (this file must NOT be under version control system)
- *.java - expected robotized and ASMified java source code (this file must be under version control system)
- *.class-replay.java - actual robotized and ASMified java source code (this file must NOT be under version control system)
If corresponding *.java and *.class-replay.java files hasn't any differences test are passed, otherwise it fails.

3) common subdirectory:
[not used and not implemented yet, but should contains mix of record and replay directories]