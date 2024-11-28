# Configuration file for the Sphinx documentation builder.
#
# For the full list of built-in configuration values, see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

# -- Project information -----------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#project-information

project = 'InfinityFit'
copyright = '2024, Team10'
author = 'Team10'
release = '1.0.0'

# -- General configuration ---------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#general-configuration

import os
import sys

# 프로젝트 경로 추가
sys.path.insert(0, os.path.abspath('../'))  # 루트 디렉토리로 경로 추가

# 확장 기능 설정
extensions = [
    'sphinx.ext.autodoc',       # 자동 문서화를 위한 확장
    'sphinx.ext.viewcode',      # 소스 코드 보기 확장
    'sphinx.ext.napoleon',      # Google 및 NumPy 스타일 docstring 지원
]

templates_path = ['_templates']
exclude_patterns = []

# -- Options for HTML output -------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#options-for-html-output

import sphinx_rtd_theme

# 테마 설정
html_theme = "sphinx_rtd_theme"
html_theme_path = [sphinx_rtd_theme.get_html_theme_path()]
