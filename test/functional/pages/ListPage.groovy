package pages

import geb.Module

class ListPage extends ScaffoldPage {
	static url = "user/list"

	static at = {
		title ==~ /User List/
	}

	static content = {
		newUserButton(to: CreatePage) { $("a", text: "New User") }
		peopleTable { $("div.content table", 0) }
		userRow { module UserRow, userRows[it] }
		userRows(required: false) { peopleTable.find("tbody").find("tr") }
	}
}

class UserRow extends Module {
	static content = {
		cell { $("td", it) }
		cellText { cell(it).text() }
        cellHrefText{ cell(it).find('a').text() }
		enabled { Boolean.valueOf(cellHrefText(0)) }
		firstName { cellText(1) }
		lastName { cellText(2) }
		showLink(to: ShowPage) { cell(0).find("a") }
	}
}