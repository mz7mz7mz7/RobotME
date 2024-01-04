
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{dweiss-technote}

\DeclareOption*{\PassOptionsToClass{\CurrentOption}{article}}
\ExecuteOptions{prepare,box}
\ProcessOptions\relax

\LoadClass[a4paper, 10pt, fleqn, final] {article}

\RequirePackage{graphicx}
\RequirePackage{fancyhdr}
\RequirePackage{datetime}
\RequirePackage{setspace}
\RequirePackage[left=2.5cm,right=2cm,top=2.5cm,bottom=2.5cm,includemp]{geometry}

\def\cvsid#1{\def\cvsiddata{#1}}
\def\cvsiddata{\relax}

\fancypagestyle{cvsidstyle} {%
\fancyhf{}\rhead{\thepage}
\lfoot{\tiny{\ddmmyydate\today{} \xxivtime{}\quad \texttt{$\ \cvsiddata\ $}}}
}
\fancypagestyle{cvsidfirst} {%
\fancyhf{}\renewcommand{\headrulewidth}{0pt}
\lfoot{\tiny{\ddmmyydate\today{} \xxivtime{}\quad \texttt{$\ \cvsiddata\ $}}}
}

% Sidebar note (keyword/ topic)

\newcommand{\sidenote}[1]{\setstretch{.5}\marginpar{\raggedright{%
	\scriptsize\textsc{\MakeLowercase{#1}}}}\setstretch{1}}


