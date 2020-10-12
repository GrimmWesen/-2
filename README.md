# LexicalAnalysis-PhaserAnalysis
编译原理的课设


词法分析、语法分析

1.通过识别3型文法，基于NFA进行单词识别，将给定的测试程序文本，输出为可识别的5种的单词符号表。

2.通过识别2型文法，基于词法分析器产生的token table，通过LL(1)自顶向下分析的语法分析方法，识别给定的程序语言是否符合所给定的语法。


输入输出文件在  软件课设2/resource下
具体实现：
Lexgrammar.txt：词法分析输入的3型文法
program.txt:词法分析器输入的待识别程序
LexOut.txt:词法分析输出的二元式结果<种类值，单词>
midd.txt：词法分析交给语法分析的细分的token字符串
Phasergrammar.txt：语法分析输入的2型文法
PhaserProcess.txt：语法分析的中间过程first、follow、select、预测分析表
PhaserOut.txt：语法分析的结果 预测分析栈的具体过程

首先执行软件课程设计2\软件课设2\src\LexicalAnalysis\LexMain.java 完成词法分析
然后执行软件课程设计2\软件课设2\src\PhaserAnalysis\PhaserMain.java 完成语法分析
