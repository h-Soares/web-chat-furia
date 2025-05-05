#!/bin/bash

ollama serve &
ollama pull gemma3:1b
ollama run gemma3:1b
tail -f /dev/null