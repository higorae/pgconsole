package com.github.luksrn.postgresql

import geb.Page

class ConsolePage extends Page	{
	static at = { title.startsWith("PGConsole") }
}
