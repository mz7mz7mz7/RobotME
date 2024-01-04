@echo off

latexmk j2me-testing.tex
dvipdfm -s 1 j2me-testing.dvi 