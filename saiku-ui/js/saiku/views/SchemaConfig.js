/*
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

/**
 * The analysis workspace
 */
var SchemaConfigGlobal;
var SchemaConfig = Backbone.View.extend({
	className: 'tab_container',

	events: {
		// 'click .openReport': 'openReport',
		'click #openReportButton': 'openReportButton',
		'click #analysIndicators': 'analysIndicators',
		'click #changeGroup': 'toggleGroup',

		'click  .select_all_indicator_row__div': 'recheckRow',
		// 'click  input.select_all_indicator_row': 'recheckRow',
		'click  .indicator_check_box': 'recheckIndicator',
		// 'click  .select_indicator_row__div': 'recheckIndicator',
		'click  .indicators_group_row': 'expandIndicatorRow',
		// 'dblclick  .report_row': 'openTargetReport',
		'click  .show_report_icon': 'openTargetReport',
		'click  .report_row': 'expandReportRow',
		'click  #filterShowAll ': 'filterShowAll',
		'click  #filterShowSelected': 'filterShowSelected',
		'click  #clearAll': 'clearAll',
		'click  #filterShowUnselected': 'filterShowUnselected',
		'click  #searchByName': 'searchByName',
		'click  #clearSearch': 'clearSearch',
		'click	.vm_clean_icon': 'clearSelectedIndicatorRows',

		'click  #searchReportByName': 'searchReportByName',

		'click  #clearReportSearch': 'clearReportSearch',
		'click  .olap_header__button.indicators_button': 'switchToIndicators2',
		'click  .olap_header__button.analyze_button': 'switchToIndicators',
		'click  .olap_header__button.reports_button': 'switchToReports',
		'click  #showMyReports': 'showMyReports',
		'click  #showOtherReports': 'showOtherReports',
		'click  #sortByReportName': 'sortByReportName',
		'click  #sortByUpdateDate': 'sortByUpdateDate',
		'click  #sortByCreateDate': 'sortByCreateDate',
		'click  #sortByUserName': 'sortByUserName',
		'click  #showAllReports': 'showAllReports',
		// 'click  #logout_btn': 'logout',
		// 'click  #logout_btn_img': 'logout',
		'click  .delete_report': 'delete_report_dialog',
		'click  .modalDeleteQuery': 'delete_report',
		'click  .close_delete_report_modal': 'close_delete_report_modal'


	},

	getParentOfIndicatorString: function (target) {
		if (target.classList.contains("indicator_check_box")) {
			return target
		} else {
			return this.getParentOfIndicatorString(target.parentElement)
		}
	},
	updateShowCount: function () {
		SchemaConfigGlobal.updateJQSelectors();
		var shownCount = $('.indicators_group_row.active > .indicator_row:not(".hide")').length;

		var allCount = $('.indicators_group_row').find('.indicator_row').length;
		var span = document.createElement("span");
		span.innerHTML = "Показано ";
		var showCountElement = $('#indicators_shown_count')[0];
		showCountElement.children = [];
		showCountElement.innerHTML = "";

		showCountElement.appendChild(span);
		showCountElement.innerHTML = showCountElement.innerHTML + shownCount + " из " + allCount;
	},
	updateSelectedCount: function () {
		SchemaConfigGlobal.updateJQSelectors();
		var selectedCount = 0,
			span = document.createElement("span"),
			selectedCountElement = $('#indicators_selected_count')[0],
			inputs = $(".indicators_table").find("input.not_expand");
		selectedCountElement.children = [];
		selectedCountElement.innerHTML = "";
		span.innerHTML = "Выбрано ";

		for (var i in inputs) {
			// if (inputs[i].checked && (inputs[i] instanceof HTMLInputElement)) {
			// 	selectedCount++;
			// }
			if ((inputs[i] instanceof HTMLInputElement) && inputs[i].parentElement.classList.contains("active")) {
				selectedCount++;
			}
		}


		// this.dimensionData.forEach(function (group) {
		//
		// if (group.factGroups !== null) {
		//   group.factGroups.forEach(function (innerGroup) {
		//     innerGroup.facts.forEach( function (indicator) {
		//       selectedCount = selectedCount + (indicator.isSelected ? 1 : 0)
		//     });
		// 	})
		// } else {
		//   group.facts.forEach(function (indicator) {
		// 		selectedCount = selectedCount + (indicator.isSelected ? 1 : 0)
		// 	})
		// }
		//
		//
		// });

		selectedCountElement.appendChild(span);
		selectedCountElement.innerHTML = selectedCountElement.innerHTML + selectedCount;

		if (selectedCount > 0) {
			$("#clearAll")[0].disabled = false;
			$("#analysIndicators")[0].disabled = false;
		} else {
			$("#clearAll")[0].disabled = true;
			$("#analysIndicators")[0].disabled = true;
		}
	},

	close_delete_report_modal: function () {

		$(".reports_modal_overlay").addClass("hide");
		$(".delete_report_success").addClass("hide");
	},

	initialize: function (args) {
		_.bindAll(this, "analysIndicators");
		_.bindAll(this, "toggleGroup");
		_.bindAll(this, "recheckRow");
		// _.bindAll(this, "goBackToIndicators");
		_.bindAll(this, "expandIndicatorRow");
		_.bindAll(this, "expandReportRow");
		_.bindAll(this, "openReportButton");
		_.bindAll(this, "openTargetReport");
		_.bindAll(this, "clearAll");
		_.bindAll(this, "searchByName");
		_.bindAll(this, "delete_report");
		_.bindAll(this, "delete_report_dialog");
		_.bindAll(this, "searchReportByName");
		_.bindAll(this, "showAllReports");
		_.bindAll(this, "showMyReports");
		_.bindAll(this, "showOtherReports");
		_.bindAll(this, "clearSearch");
		_.bindAll(this, "clearSelectedIndicatorRows");
		_.bindAll(this, "clearReportSearch");
		_.bindAll(this, "recheckIndicator");
		_.bindAll(this, "filterShowUnselected");
		_.bindAll(this, "filterShowSelected");
		_.bindAll(this, "switchToIndicators2");
		_.bindAll(this, "switchToIndicators");
		_.bindAll(this, "switchToReports");
		_.bindAll(this, "filterShowAll");
		this.xmlSchema = $();
		this.schemas = new Schemas({}, {dialog: this});
		SchemaConfigGlobal = this;


	},
	openTargetReport: function (event) {
		SchemaConfigGlobal.updateJQSelectors();
		var target = event.target || event;


		if (!target.classList.contains("report_row")) {
			arguments.callee(target.parentElement)
		} else {
			var reportName = target.getAttribute("report-name"),
				reportId = +target.getAttribute("report-id"),
				updateDate = target.getAttribute("update-date"),
				isOwner = target.getAttribute("is-owner") === "true";
			SchemaConfigGlobal.openReport(null, reportId, isOwner, reportName, updateDate);
		}


	},
	caption: function (increment) {
		if (increment) {
			Saiku.tabs.queryCount++;
		}
		return "<span class='i18n'>Schema config</span> (" + (Saiku.tabs.queryCount) + ")";
	},
	header: $("#header"),

	constTemplate:

	"<div class='full_page_form indicators_form'><div class='indicators_header'>" +

	"<input place-holder='Поиск' class='indicators_header__search_box'>" +
	"<div id='searchByName' class='indicators_header__search_button' ></div>" +
	"<div id='clearSearch'class='indicators_header__clear_search_button hide' ></div>" +
	"<div class='indicators_header__buttons_box'><label>Показывать</label>" +
	"<button id='filterShowAll' class='indicators_header__buttons_box__button left_button active'>Все</button>" +
	"<button id='filterShowSelected' class='indicators_header__buttons_box__button'>Отмеченные</button>" +
	"<button id='filterShowUnselected' class='indicators_header__buttons_box__button right_button'>Неотмеченные</button>" +
	"<button disabled='disabled' id='clearAll' class='indicators_header__buttons_box__button img_into_button clear_button'><div></div>Очистить выборку</button>" +
	"<button class='indicators_header__buttons_box__button img_into_button change_group_button' id='changeGroup'><div></div>Сменить группировку</button>" +
	"<button disabled='disabled' class='indicators_header__buttons_box__button img_into_button analysis_button'  id='analysIndicators'><div></div>Анализировать выбранные</button>" +
	"</div>" +
	"</div>" +
	"<div class='indicators_table'>" +
	"<div class='indicators_table__header'>" +
	"<div class=\"vm_clean_icon\"></div><div>Группа</div><div>Значение</div><div>Единицы измерения</div><div>Обновлено</div>" +
	"</div>" +
	"<div class='table_content_block'>" +
	"<% _.each(measureGroups, function(mg,index) { %> " +
	"<div index='<%= index %>' class='indicators_group_row <%= mg.isSelected? \"active\":\"\" %>'>" +
	"<div class='title-item'>" +
	"<div class=\"select_all_indicator_row__div\"><input class='select_all_indicator_row' type='checkbox' index='<%= index %>'></div>" +
	"<div class='expanded indicators_group_row__arrow'></div><%= mg.name %></div>" +
	"<% _.each(mg.facts, function(fact,factIndex) { %> " +
	"<div class='indicator_row indicator_check_box not_expand'>" +
	"<div class=\"select_indicator_row__div\"><input index='<%= index %>' fact-id='<%= fact.id %>' fact-index='<%= factIndex %>'class=' not_expand' type='checkbox' ></div>" +
	"<div index='<%= index %>' fact-index='<%= factIndex %>' class=' not_expand' ><%= fact.name %></div>" +
	"<div class='not_expand'><%= fact.updateDate %></div>" +
	"<div class='not_expand'><%= fact.value %></div>" +
	"<div class='not_expand'><%= fact.unitName %></div>" +
	"</div>" +
	"<% }) %>" +
	"</div>" +
	"<% }) %>" +
	"</div>" +
	"<div class='bottom_panel'>	" +
	"<div id='indicators_shown_count'>Показано </span></div>" +
	"<div id='indicators_selected_count'><span>Выбрано </span> 0</div>" +
	"</div></div></div>" +

	"<div id='report_list_section'></div>"
	,

	reportTemplate: "<div class='full_page_form reports_form hide'>" +
	/*// "<div class='reports_header'>" +
	// "<input place-holder='Поиск' class='reports_header__search_box'>" +
	// "<div id='searchReportByName' class='reports_header__search_button' ></div>" +
	// "<div id='clearReportSearch'class='reports_header__clear_search_button hide' ></div>" +
	// "<div class='reports_header__buttons_box'><label>Показывать</label>" +
	// "<button id='showMyReports' class='reports_header__buttons_box__button left_button active'>Мои отчеты</button>" +
	// "<button id='showOtherReports' class='reports_header__buttons_box__button'>Отчеты других пользователей</button>" +
	// "<button id='showAllReports' class='reports_header__buttons_box__button right_button'>Все отчеты</button>" +
	// "<button class='reports_header__buttons_box__button open_report_button' id='openReportButton'><div></div>Открыть отчет</button>" +
	// "</div>" +
	// "</div>" +*/
	"<div class='reports_table'>" +
	"<div class='reports_table__header'>" +
	"<div id='sortByReportName'>Отчет</div>" +
	"<div>Показатель</div>" +
	"<div id='sortByUpdateDate'>Дата обновления</div>" +
	"<div id='sortByCreateDate'>Дата создания</div>" +
	"<div id='sortByUserName'>Пользователь</div>" +
	"<div></div>" +
	"</div>" +
	"<div class='table_content_block'>" +
	"<% _.each(reports, function(report,index) { %> " +
	"<div index='<%= index %>' update-date='<%= report.dtChange%>' report-name='<%= report.rname %>' is-owner='<%= report.isOwner %>' report-id='<%= report.reportId %>' class='report_row <%= report.isSelected? \"active\":\"\" %>'>" +
	"<div>" +
	"<div class='expanded report_row__arrow'></div>" +
	"<div  index='<%= index %>'><span class='openReport' report-id='<%= report.reportId %>'><%= report.rname %></span></div>" +
	"<div ><%= report.dtChange %></div>" +
	"<div ><%= report.dtCreate %></div>" +
	"<div ><%= report.userName %></div>" +
	"<div class=' <%= report.isOwner ? 'delete_report_icon':'' %>'></div>" +
	"<div class=' <%= report.isOwner ? 'show_report_icon':'' %>'></div>" +
	"</div>" +
	"<% _.each(report.reportMeasures, function(measure,measureIndex) { %> " +
	"<div class='report_row_indicator not_expand'>" +
	"<div index='<%= index %>' measure-index='<%= measureIndex %>' class='not_expand'><%= measure.measureName %></div>" +
	"<div class='not_expand'><%= measure.value %></div>" +
	"<div class='not_expand'><%= measure.unitName %></div>" +
	"<div class='not_expand'><%= measure.updateDate %></div>" +

	"</div>" +
	"<% }) %>" +
	"</div>" +
	"<% }) %>" +
	"</div>" +
	"</div></div>" +
	"<div class='reports_modal_overlay hide'>	<div class='delete_report_modal delete_report_success hide'>		<span>Отчет успешно удален</span>		<div class='modal_bottom_line'></div>		<div class='modal_bottom_buttons'>			<button class='close_delete_report_modal'>Ок</button>		</div>	</div><div class='save_report_modal delete_report_modal_dialog hide'>	<span>Удаление отчета</span>	<div class='middle_area'>		<span>Название отчета</span><span id='delete_report_name_span'></span>		<span>Изменен</span><span id='delete_report_update_date_span'></span>	</div>	<span class='update_confirm_span'>Вы уверены что хотите удалить выбранный отчет?</span><div class='modal_bottom_line'></div>	<div class='modal_bottom_buttons'>		<button class='close_delete_report_modal'>Отмена</button>		<button class='modalDeleteQuery'>Удалить</button>	</div></div></div>",

	showMyReports: function () {
		SchemaConfigGlobal.updateJQSelectors();
		this.showAllReports();
		$('#showMyReports').addClass("active");
		$('#showAllReports').removeClass("active");
		$('#showOtherReports').removeClass("active");


		var reports = $(".report_row");
		for (var i in reports) {
			if (reports[i] instanceof HTMLDivElement && reports[i].getAttribute("is-owner") === "false") {
				$(reports[i]).addClass("hide");
			}
		}

	},
	delete_report: function (event) {
		SchemaConfigGlobal.updateJQSelectors();
		event.preventDefault();
		var id = this.deleteReportId;
		Loader.on();

		$.ajax({
			url: "saiku/rest/api/report/" + id,
			type: 'DELETE',
			success: function () {
				Loader.off();
				$(".reports_modal_overlay").removeClass("hide");
				$(".delete_report_success").removeClass("hide");
				var reports = $(".report_row");
				for (var i in reports) {
					if ((reports[i] instanceof HTMLDivElement) && (reports[i].getAttribute("report-id") === id)) {
						reports[i].remove()
					}
				}
				$(event.target.parentElement.parentElement).addClass("hide")
			}
		});


	},
	delete_report_dialog: function (event) {
		SchemaConfigGlobal.updateJQSelectors();
		var el = event.target.parentElement.parentElement;
		$("#delete_report_name_span")[0].innerHTML = el.getAttribute("report-name");
		$("#delete_report_update_date_span")[0].innerHTML = el.getAttribute("update-date");


		$(".delete_report_modal_dialog").removeClass("hide");
		$(".reports_modal_overlay").removeClass("hide");
		this.deleteReportId = el.getAttribute("report-id");

	},
	openReportButton: function () {
		SchemaConfigGlobal.updateJQSelectors();
		var target = $('.report_row.selected');
		if (target.length === 0) {
			return
		}
		var reportName = target[0].getAttribute("report-name"),
			reportId = +target[0].getAttribute("report-id"),
			updateDate = target[0].getAttribute("update-date"),
			isOwner = target[0].getAttribute("is-owner") === "true";
		SchemaConfigGlobal.openReport(null, reportId, isOwner, reportName, updateDate);

		// SchemaConfigGlobal.openReport(null, +target[0].getAttribute("report-id"), target[0].getAttribute("is-owner") === "true");
	},
	showAllReports: function () {
		SchemaConfigGlobal.updateJQSelectors();
		$('#showMyReports').removeClass("active");
		$('#showAllReports').addClass("active");
		$('#showOtherReports').removeClass("active");

		var reports = $(".report_row");
		reports.removeClass("hide");
	},
	showOtherReports: function () {
		this.showAllReports();
		$('#showMyReports').removeClass("active");
		$('#showAllReports').removeClass("active");
		$('#showOtherReports').addClass("active");

		var reports = $(".report_row");


		for (var i in reports) {
			if (reports[i] instanceof HTMLDivElement && reports[i].getAttribute("is-owner") === "true") {
				$(reports[i]).addClass("hide");
			}
		}
	},

	template: function () {
		// this.renderReports();
		var mainThis = this;
		return new Promise(function (resolve, reject) {
			Promise.all([
				mainThis.getFacts()
				// mainThis.getReports()
			]).then(function (results) {
				var facts = results[0];
				// reports = results[1];

				mainThis.dimensionData = facts;
				mainThis.dimensionDataForAdding = JSON.parse(JSON.stringify(facts));
				var allCount = 0;
				facts.forEach(function (cube) {
					allCount = allCount + cube.facts.length
				});
				mainThis.allFactsCount = allCount;
				// mainThis.reportsData = reports;
				resolve(_.template(mainThis.constTemplate)({
					cube_navigation: Saiku.session.sessionworkspace.cube_navigation,
					dimensions: facts,
					measureGroups: facts
					// reports: reports
				}))
			})
		});
	},

	getTemplate: function () {

	},

	getFacts: function () {
		var mainThis = this;
		return new Promise(function (resolve) {
			$.get("saiku/rest/api/facts/").then(function (facts) {
				resolve(facts);
			});


		});
	},
	getReports: function () {
		var mainThis = this;
		return new Promise(function (resolve) {
			$.get("saiku/rest/api/reports").then(function (reports) {
				resolve(reports)
			});
		});
	},


	refresh: function (e) {
		if (e) {
			e.preventDefault();
		}
		Saiku.session.sessionworkspace.refresh();
	},

	render: function () {
		var mainThis = this;
		this.load_schema().then(function () {
			var parent = $(".full_page_form.indicators_form")[0];
			parent.parentElement.insertBefore(indicatorsHeaderVm.$el, parent.parentElement.firstChild);
			parent.parentElement.insertBefore(reportsHeaderVm.$el, parent.parentElement.firstChild);
			parent.parentElement.insertBefore(indicatorsTabContent.$el, parent.parentElement.firstChild);
			$(".olap_header__button.reports_button")[0].onclick = mainThis.switchToReports;
			$(".olap_header__button.analyze_button")[0].onclick = mainThis.switchToIndicators;
			$(".olap_header__button.indicators_button")[0].onclick = mainThis.switchToIndicators2;

		});
	},
	switchToIndicators2: function () {
		this.goBackToIndicators();
		$(".olap_header__button.reports_button").removeClass("active");
		$(".olap_header__button.analyze_button").removeClass("active");
		$(".olap_header__button.indicators_button").addClass("active");

		$('.full_page_form.indicators_form').addClass("hide");
		$('#vm_indicators').addClass("hide");
		$('#vm_reports').addClass("hide");
		$('.full_page_form.reports_form').addClass("hide");


		$('#indicators_tab_content').removeClass("hide");


	},

	reinitialization: function (item, selector) {
		if (item.length === 0) {
			return $(selector);
		} else {
			return item
		}
	},

	updateJQSelectors: function () {
		this.filterShowAllButton = $('#filterShowAll');
		this.filterShowSelectedButton = $('#filterShowSelected');
		this.filterShowUnselectedButton = $('#filterShowUnselected');
		this.cubeRows = $('.indicators_group_row');
		this.factRows = $('.indicator_row');
		this.reportRows = $('.report_row');
		this.reportIndicatorsRows = $('.report_row_indicator');
	},

	searchByName: function () {

		SchemaConfigGlobal.updateJQSelectors();

		var searchInputValue = $(".indicators_header__search_box")[0].value,
			text = searchInputValue.toLowerCase(),
			originalText = searchInputValue;
		this.clearSearch();
		$(".indicators_header__search_box")[0].value = originalText;

		for (var i in this.factRows) {
			if (this.factRows[i] instanceof HTMLDivElement) {
				if ($(this.factRows[i]).find("div")[0].innerHTML.toLowerCase().indexOf(text) === -1) {
					$(this.factRows[i]).addClass("hide");
				}
			}
		}

		for (i in this.cubeRows) {
			if (this.cubeRows[i] instanceof HTMLDivElement) {
				if ($(this.cubeRows[i]).find("div.indicator_row:not('.hide')").length === 0) {
					$(this.cubeRows[i]).addClass("hide");
				}
			}
		}
		$('#clearSearch').removeClass("hide");
		this.updateShowCount();
	},

	vSearchByName: function (text, values) {
		this.clearSearch();
		if (text && text.length > 1) {
			for (var i in this.factRows) {
				if (this.factRows[i] instanceof HTMLDivElement) {
					if ($(this.factRows[i]).find("div")[1].innerHTML.toLowerCase().indexOf(text) === -1) {
						$(this.factRows[i]).addClass("hide");
					}
				}
			}

			for (i in this.cubeRows) {
				if (this.cubeRows[i] instanceof HTMLDivElement) {
					if ($(this.cubeRows[i]).find("div.indicator_row:not('.hide')").length === 0) {
						$(this.cubeRows[i]).addClass("hide");
					}
				}
			}
			this.updateShowCount();
		}
	},

	searchReportByName: function () {

		SchemaConfigGlobal.updateJQSelectors();

		if (this.reportRows.length === 0) {
			this.reportRows = $('.report_row');
		}
		var searchInputValue = $(".vm_reports.search_input")[0].value,
			text = searchInputValue.toLowerCase(),
			originalText = searchInputValue;
		this.clearReportSearch();
		$(".vm_reports.search_input")[0].value = originalText;


		for (i in this.reportRows) {
			if (this.reportRows[i] instanceof HTMLDivElement) {
				$(this.reportRows[i]).addClass("hide_for_search");
				$(this.reportRows[i]).addClass("active");
			}
		}
		for (i in this.reportRows) {
			if (this.reportRows[i] instanceof HTMLDivElement) {
				if (this.containsWord(this.reportRows[i].getAttribute("report-name"), text)
					|| this.containsWord($(this.reportRows[i]).find("div > div:nth-child(5)")[0].innerHTML, text)) {
					$(this.reportRows[i]).removeClass("hide_for_search");
				}
			}
		}

		for (var i in this.reportIndicatorsRows) {
			if (this.reportIndicatorsRows[i] instanceof HTMLDivElement) {
				var curRow = $(this.reportIndicatorsRows[i]);

				if (
					this.containsWord($(curRow[0]).find("div:nth-child(3)")[0].innerHTML, text) ||
					this.containsWord($(curRow[0]).find("div:nth-child(1)")[0].innerHTML, text)
				) {
					curRow.parent().removeClass("hide_for_search");
				}
				// if ($(this.reportIndicatorsRows[i]).find("div")[0].innerHTML.toLowerCase().indexOf(text) !== -1) {
				// 	curRow.parent().removeClass("hide_for_search");
				// }
			}
		}

		$('#clearReportSearch').removeClass("hide");
	},

	containsWord: function (itemText, word) {
		itemText = itemText.toLowerCase();
		return itemText.indexOf(word) > -1;
	},

	switchToIndicators: function () {
		$(".olap_header__button.reports_button").removeClass("active");
		$(".olap_header__button.analyze_button").addClass("active");
		$(".olap_header__button.indicators_button").removeClass("active");

		$('.full_page_form.indicators_form').removeClass("hide");
		$('#vm_indicators').removeClass("hide");
		$('#vm_reports').addClass("hide");
		$('.full_page_form.reports_form').addClass("hide");
		$('#indicators_tab_content').addClass("hide");
		$('.olap_header__button.left_button').addClass("active");
		$('.olap_header__button.right_button').removeClass("active");
		$(".go_back_to_reports").removeClass("shown");
		this.updateSelectedCount();
		this.updateShowCount();

	},
	goBackToIndicators: function () {
		$("#header").find(".tabs ul li:nth-child(2) > span").click();
		SchemaConfigGlobal.reportName = null;
		$(".go_back_to_indicators").removeClass("shown");
		var contetntTab = $("#tab_panel");

		contetntTab.removeClass("fix_height_by75");

	},

	switchToReports: function () {

		$(".olap_header__button.reports_button").addClass("active");
		$(".olap_header__button.analyze_button").removeClass("active");
		$(".olap_header__button.indicators_button").removeClass("active");
		$('#indicators_tab_content').addClass("hide");
		$('.vm_reports.search_input').val('');

		Loader.on();
		var mainThis = this;
		SchemaConfigGlobal.renderReports().then(function () {
			Loader.off();
			mainThis.goBackToIndicators();
			mainThis.showMyReports();
			$('.full_page_form.indicators_form').addClass("hide");
			$('#vm_indicators').addClass("hide");
			$('#vm_reports').removeClass("hide");
			$('.full_page_form.reports_form').removeClass("hide");
			$('.olap_header__button.left_button').removeClass("active");
			$('.olap_header__button.right_button').addClass("active");
			mainThis.updateSelectedCount();
			mainThis.updateShowCount();
		});

	},

	clearSearch: function () {
		SchemaConfigGlobal.updateJQSelectors();

		for (var i in this.factRows) {
			if (this.factRows[i] instanceof HTMLDivElement) {
				$(this.factRows[i]).removeClass("hide");

			}
		}
		for (var i in this.cubeRows) {
			if (this.cubeRows[i] instanceof HTMLDivElement) {
				$(this.cubeRows[i]).removeClass("hide");
			}
		}
		$(".indicators_header__search_box")[0].value = "";
		$('#clearSearch').addClass("hide");
		this.updateShowCount();
	},

	clearSelectedIndicatorRows: function () {
		$('.select_all_indicator_row__div.active').removeClass('active');
		$('.indicator_row > .select_indicator_row__div.active')
			.removeClass('active')
			.children('input[type=checkbox]').prop('checked', false);
		this.updateSelectedCount();
	},

	clearReportSearch: function () {
		SchemaConfigGlobal.updateJQSelectors();

		for (var i in this.reportIndicatorsRows) {
			if (this.reportIndicatorsRows[i] instanceof HTMLDivElement) {
				$(this.reportIndicatorsRows[i]).removeClass("hide");
				$(this.reportIndicatorsRows[i]).removeClass("hide_for_search");

			}
		}
		for (var i in this.reportRows) {
			if (this.reportRows[i] instanceof HTMLDivElement) {
				$(this.reportRows[i]).removeClass("hide");
				$(this.reportRows[i]).removeClass("hide_for_search");
				$(this.reportRows[i]).removeClass("active");
			}
		}
		$(".vm_reports.search_input")[0].value = "";
		$('#clearReportSearch').addClass("hide");

		$(".report_row:first-child").addClass("selected");
		$(".report_row:first-child").addClass("active");


	},

	filterShowUnselected: function (event) {
		SchemaConfigGlobal.updateJQSelectors();
		this.deselectAllFilters();
		this.filterShowUnselectedButton.addClass("active");
		for (var i in this.factRows) {
			if (this.factRows[i] instanceof HTMLDivElement) {
				if ($(this.factRows[i]).find("input")[0].checked) {
					$(this.factRows[i]).addClass("hide");
				} else {
					$(this.factRows[i]).removeClass("hide");
				}
			}
		}
		for (var i in this.cubeRows) {
			if (this.cubeRows[i] instanceof HTMLDivElement) {
				if ($(this.cubeRows[i]).find("div.indicator_row:not('.hide')").length === 0) {
					$(this.cubeRows[i]).addClass("hide");
				} else {
					$(this.cubeRows[i]).removeClass("hide");
				}
			}
		}
		this.updateShowCount();

	},

	filterShowSelected: function (event) {


		SchemaConfigGlobal.updateJQSelectors();
		this.deselectAllFilters();
		this.filterShowSelectedButton.addClass("active");
		for (var i in this.factRows) {
			if (this.factRows[i] instanceof HTMLDivElement) {
				if (!$(this.factRows[i]).find("input")[0].checked) {
					$(this.factRows[i]).addClass("hide");
				} else {
					$(this.factRows[i]).removeClass("hide");
				}
			}
		}
		for (var i in this.cubeRows) {
			if (this.cubeRows[i] instanceof HTMLDivElement) {
				if ($(this.cubeRows[i]).find("div.indicator_row:not('.hide')").length === 0) {
					$(this.cubeRows[i]).addClass("hide");
				} else {
					$(this.cubeRows[i]).removeClass("hide");
				}
			}
		}
		this.updateShowCount();
	},

	filterShowAll: function (event) {
		SchemaConfigGlobal.updateJQSelectors();
		this.deselectAllFilters();
		this.filterShowAllButton.addClass("active");
		for (var i in this.cubeRows) {
			if (this.cubeRows[i] instanceof HTMLDivElement) {
				$(this.cubeRows[i]).removeClass("hide");
			}
		}
		for (var i in this.factRows) {
			if (this.factRows[i] instanceof HTMLDivElement) {
				$(this.factRows[i]).removeClass("hide")
			}
		}
		this.updateShowCount();
	},

	clearAll: function (event) {
		SchemaConfigGlobal.updateJQSelectors();
		var inputs = $(".indicators_table").find("input");
		for (var i in inputs) {
			inputs[i].checked = false;
		}
		this.dimensionData.forEach(function (group) {
			if (group.facts) {
				group.facts.forEach(function (indicator) {
					indicator.isSelected = false;
				})
			}
		});

		$("#clearAll")[0].disabled = "disabled";
		$("#analysIndicators")[0].disabled = "disabled";
		this.updateSelectedCount();
	},

	deselectAllFilters: function () {
		this.filterShowUnselectedButton.removeClass("active");
		this.filterShowSelectedButton.removeClass("active");
		this.filterShowAllButton.removeClass("active");
	},

	recheckRow: function (event) {
		event.preventDefault();

		if (event.target instanceof HTMLInputElement) {
			event.target = event.target.parentElement;
		}
		var curStatus = !(event.target.checked === false || (event.target.classList.contains("active"))),
			inputs = $(event.target.parentElement.parentElement).find("input"),
			checkDiv = $(event.target.parentElement.parentElement).find(".select_all_indicator_row__div")[0],
			childDivs = $(checkDiv.parentElement.parentElement).find(".select_indicator_row__div"),
			parentIndex = $(event.target).closest('.indicators_group_row.active:not(.inner)').attr('index');
		for (var i in inputs) {
			if (inputs[i] instanceof HTMLInputElement) {
				inputs[i].checked = curStatus;
			}
		}

		$(checkDiv).toggleClass("active");

		for (var i in childDivs) {
			if (childDivs[i] instanceof HTMLDivElement && childDivs[i].classList.contains("select_indicator_row__div")) {
				$(childDivs[i]).toggleClass("active", curStatus);
			}
		}


		if (this.dimensionData[0].factGroups !== null && this.dimensionData[parentIndex]) {
			this.dimensionData[parentIndex].factGroups[event.target.children[0].getAttribute("index")].facts.forEach(function (fact) {
				fact.isSelected = curStatus;
			});
		} else {
			this.dimensionData[event.target.children[0].getAttribute("index")].facts.forEach(function (fact) {
				fact.isSelected = curStatus;
			});
		}


		this.updateSelectedCount();
	},

	recheckIndicator: function (event) {
		try {
			var inputDiv = $(this.getParentOfIndicatorString(event.target)).find(".select_indicator_row__div")[0];
			if (event.target.classList.contains("select_indicator_row__div")) {
				// inputDiv = event.target;
				event.target = event.target.children[0];
			}

			// if (event.target.parentElement.classList.contains("select_indicator_row__div")) {
			// 	inputDiv = event.target.parentElement;
			// }
			var target = $(event.target.parentElement).find("input")[0];
			if (event.target.className.indexOf("indicator_row") > -1) {
				target = $(event.target).find("input")[0];
			}
			if (!(event.target instanceof HTMLInputElement)) {
				target.checked = !target.checked;

			}

			if (inputDiv.classList.contains("active")) {
				inputDiv.classList.remove("active")
			} else {
				inputDiv.classList.add("active");
			}

			var curStatus = target.checked,
				measureIndex = target.getAttribute("index"),
				parentIndex = $(event.target).closest('.indicators_group_row.active:not(.inner)').attr('index');
			factIndex = target.getAttribute("fact-index");
			if (!curStatus) {
				$(target.parentElement.parentElement).find("input.select_all_indicator_row")[0].checked = false;
			}

			if (this.dimensionData[parentIndex].factGroups !== null &&
				this.dimensionData[parentIndex].factGroups[measureIndex]) {
				this.dimensionData[parentIndex].factGroups[measureIndex].facts[factIndex].isSelected = curStatus;
			} else {
				if (this.dimensionData[measureIndex].facts) {
					this.dimensionData[measureIndex].facts[factIndex].isSelected = curStatus;
				}
			}


			var allChecked = true,
				internalInputs = $(target.parentElement.parentElement).find("input").slice(1);
			for (var i in internalInputs) {
				if (internalInputs[i].checked === false && internalInputs[i] instanceof HTMLInputElement) {
					allChecked = false;
				}
			}

			if (allChecked) {
				$(target.parentElement.parentElement).find("input.select_all_indicator_row")[0].checked = true;
				$(target.parentElement.parentElement).find(".select_all_indicator_row__div")[0].addClass("active");

			}
		} catch (e) {
		}

		this.updateSelectedCount();

	},

	analysIndicators: function (event) {
		if (event && event.preventDefault) {
			event.preventDefault();
		}

		var ids = [];
		$(".indicator_row > .select_indicator_row__div.active > input[type=checkbox]")
			.each(function (index, elem) {
				ids.push(elem.getAttribute('fact-id'));
			});
		// this.dimensionData.forEach(function (item) {
		// 	for (var i in inputs) {
		// 		if (inputs[i] instanceof HTMLInputElement && inputs[i].checked) {
		// 			ids.push(inputs[i].getAttribute("fact-id"));
		// 		}
		// 	}
		// 	// if (item.factGroups !== null) {
		// 	//
		// 	//   item.factGroups.forEach(function (innerGroup) {
		// 	//     innerGroup.facts.forEach( function (fact) {
		// 	//       if (fact.isSelected===true) {
		// 	// 				ids.push(fact.id)
		// 	// 			}
		// 	//     });
		// 	//   });
		// 	//
		// 	// } else {
		// 	//   item.facts.forEach(function (fact) {
		// 	// 		if (fact.isSelected===true) {
		// 	// 			ids.push(fact.id)
		// 	// 		}
		// 	// 	})
		// 	// }
        //
		// });

		ids = _.uniq(ids);
		if (ids.length === 0) return;
		ids = ids.join(",");
		var parentThis = this;
		Loader.on();
		SchemaConfigGlobal.reportId = null;
		$.get("saiku/rest/api/scheme/" + Saiku.session.username + "/" + Saiku.session.sessionid + "/" + ids).then(function (res) {
			globalWorkspace.refresh();
			setTimeout(function () {
				parentThis.header.find(".tabs ul li:nth-child(2) > a").click();  //Переходим на вкладку с анализом
				$("#cubesselect").find("optgroup option").attr('selected', 'true'); //Выбираем схему
				globalWorkspace.new_query();  //Обновляем схему
				$(".go_back_to_indicators").addClass("shown"); //Показывем заголовок с кнопкой "Назад"

				var contetntTab = $("#tab_panel");
				contetntTab.addClass("fix_height_by75");
				// contetntTab.addClass("round_tab");

				var dimensions = $('.sidebar_inner.dimension_tree .parent_dimension > a');
				if (dimensions.length > 0) {
					Loader.off();
					dimensions.click(); //разворачиваем все измерения
					$('.measure_tree ul li.d_measure a').click();  //выбираем все показатели


					var org = $('ul.d_hierarchy[hierarchy="[dimension_20].[dimension_20]"]').find('a[level="hierarchy_12"]'),
						calendar = $('ul.d_hierarchy[hierarchy="[dimension_2].[dimension_2]"]').find('a[level="hierarchy_4"]');

					parentThis.select_dimension(org, 1);
					parentThis.select_dimension(calendar, 1);
					globalWorkspace.query.run(true);


					parentThis.addSomeFunctions();

					var reportNameElemement = document.createElement("div");
					reportNameElemement.setAttribute("id", "save_report_name");
					reportNameElemement.innerHTML = "Анализ показателей";
					$(".workspace_results_titles")[0].append(reportNameElemement);
					$(".save_new_report .report_name_input")[0].value = "Анализ показателей";


				}
			}, 4000);


		});


	},

	addSomeFunctions: function () {
		// var autoUpdate = $('#automatic_icon');
		// autoUpdate.click= function () {
		// 	autoUpdate.find('input')[0].checked = !autoUpdate.find('input')[0].checked
		// };
		// autoUpdate.find('input')[0].checked = false;
	},

	toggleGroup: function (event) {
		event.preventDefault();
		var mainThis = this;
		console.log(mainThis);
		var btn = $('#changeGroup');
		var list = btn.next();

		if (list.length > 0 && list.is('.group-list')) {
			list.remove();
		} else {

			// добавляем дропдаун со списком группировок
			$.get("saiku/rest/api/groups").then(function (res) {
				var templateList = $("<ul class='group-list'></ul>");
				for (var i = 0; i < res.length; i++) {
					var groupId = res[i].groupId;
					var groupName = res[i].groupName;
					var item = $("<li class='group-list__item' id=" + groupId + ">" + groupName + "</li>");

					// начало обработки клика
					item.click({groupId: groupId, mainThis: mainThis}, function (event) {

						Loader.on();
						// собираем таблицу показателей, только контентная часть
						$.get("saiku/rest/api/facts/" + event.data.groupId).then(function (facts) {
							var contentBlock = $('<div class="table_content_block"></div>');
							var indicatorsGroupRow = $('<div class="indicators_group_row" index=""></div>');
							var existTableContent = $('.indicators_form .indicators_table__header');

							// цикл по верхнему уровню
							for (var i = 0; i < facts.length; i++) {
								var item = facts[i];

								// пропускаем пустые элементы
								if (item.factGroups !== null) {
									if (item.factGroups.length === 0) {
										break;
									}
								} else if (item.facts.length === 0) {
									break;
								}

								var mainTitleContainer = $('<div class="title-item"><div class="select_all_indicator_row__div"><input class="select_all_indicator_row" type="checkbox" index="' + i + '"></div><div class="expanded indicators_group_row__arrow"></div>' + item.name + '</div>');
								var indicatorClone = indicatorsGroupRow.clone(true);
								var indicator;
								var indicatorRow;

								indicatorClone.append(mainTitleContainer);

								if (item.factGroups !== null) {

									// цикл по вложенным элементам c factGroups
									for (var k = 0; k < item.factGroups.length; k++) {
										var factGroupInnerContainer = indicatorsGroupRow.clone(true);
										var factGroup = item.factGroups[k];
										var factGroupTitleContainer = $('<div class="title-item"><div class="select_all_indicator_row__div"><input class="select_all_indicator_row" type="checkbox" index="' + k + '"></div><div class="expanded indicators_group_row__arrow"></div>' + factGroup.name + '</div>');
										factGroupInnerContainer.attr('index', k);
										factGroupInnerContainer.addClass('inner');

										factGroupTitleContainer.click(function (event) {
											if ($(event.target).is('input') === false)
												$(this).closest('.indicators_group_row.inner').toggleClass('active');
										});

										factGroupInnerContainer.append(factGroupTitleContainer);

										// цикл по нижнему уровню
										for (var j = 0; j < factGroup.facts.length; j++) {
											var innerItem = factGroup.facts[j];
											var innerRow = $('<div class="indicator_row indicator_check_box not_expand"><div class="select_indicator_row__div"><input fact-id=' + factGroup.facts[j].id + ' index=' + i + ' fact-index=' + j + ' class=" not_expand" type="checkbox"></div><div index=' + i + ' fact-index=' + j + ' class=" not_expand" title="Перейти к анализу показателя">' + innerItem.name + '</div><div class="not_expand">' + innerItem.updateDate + '</div><div class="not_expand">' + innerItem.value + '</div><div class="not_expand">' + innerItem.unitName + '</div></div>');
											factGroupInnerContainer.append(innerRow);
										}

										indicatorClone.append(factGroupInnerContainer);
									}
								} else {

									// цикл по вложенным элементам
									for (var l = 0; l < item.facts.length; l++) {
										indicator = item.facts[l];
										indicatorRow = $('<div class="indicator_row indicator_check_box not_expand"><div class="select_indicator_row__div"><input fact-id=' + item.facts[l].id + ' index=' + i + ' fact-index=' + l + ' class=" not_expand" type="checkbox"></div><div index=' + i + ' fact-index=' + l + ' class=" not_expand" title="Перейти к анализу показателя">' + indicator.name + '</div><div class="not_expand">' + indicator.updateDate + '</div><div class="not_expand">' + indicator.value + '</div><div class="not_expand">' + indicator.unitName + '</div></div>');

										indicatorClone.append(indicatorRow);
									}
								}

								indicatorClone.attr('index', i);
								contentBlock.append(indicatorClone);
							}

							existTableContent.next().remove();
							existTableContent.after(contentBlock);

							mainThis.factRows = $('.indicator_row');
							mainThis.cubeRows = $('.indicators_group_row');
							mainThis.dimensionData = facts;
							mainThis.updateShowCount();
							mainThis.updateSelectedCount();
							mainThis.filterShowAll();
							Loader.off();

						});

						$(this).closest('.group-list').remove();
					});

					templateList.append(item);
				}

				btn.after(templateList);
			});

		}
		this.updateShowCount();

	},

	changeGroup: function (groupId) {
		Loader.on();
		var mainThis = this;
		$.get("saiku/rest/api/facts/" + groupId).then(function (facts) {
			var contentBlock = $('<div class="table_content_block"></div>');
			var indicatorsGroupRow = $('<div class="indicators_group_row" index=""></div>');
			var existTableContent = $('.indicators_form .indicators_table__header');

			// цикл по верхнему уровню
			for (var i = 0; i < facts.length; i++) {
				var item = facts[i];

				// пропускаем пустые элементы
				if (item.factGroups !== null) {
					if (item.factGroups.length === 0) {
						break;
					}
				} else if (item.facts.length === 0) {
					break;
				}

				var mainTitleContainer = $('<div class="title-item"><div class="select_all_indicator_row__div"><input class="select_all_indicator_row" type="checkbox" index="' + i + '"></div><div class="expanded indicators_group_row__arrow"></div>' + item.name + '</div>');
				var indicatorClone = indicatorsGroupRow.clone(true);
				var indicator;
				var indicatorRow;

				indicatorClone.append(mainTitleContainer);

				if (item.factGroups !== null) {

					// цикл по вложенным элементам c factGroups
					for (var k = 0; k < item.factGroups.length; k++) {
						var factGroupInnerContainer = indicatorsGroupRow.clone(true);
						var factGroup = item.factGroups[k];
						var factGroupTitleContainer = $('<div class="title-item"><div class="select_all_indicator_row__div"><input class="select_all_indicator_row" type="checkbox" index="' + k + '"></div><div class="expanded indicators_group_row__arrow"></div>' + factGroup.name + '</div>');
						factGroupInnerContainer.attr('index', k);
						factGroupInnerContainer.addClass('inner');

						factGroupTitleContainer.click(function (event) {
							if ($(event.target).is('input') === false)
								$(this).closest('.indicators_group_row.inner').toggleClass('active');
						});

						factGroupInnerContainer.append(factGroupTitleContainer);

						// цикл по нижнему уровню
						for (var j = 0; j < factGroup.facts.length; j++) {
							var innerItem = factGroup.facts[j];
							var innerRow = $('<div class="indicator_row indicator_check_box not_expand"><div class="select_indicator_row__div"><input fact-id=' + factGroup.facts[j].id + ' index=' + i + ' fact-index=' + j + ' class=" not_expand" type="checkbox"></div><div index=' + i + ' fact-index=' + j + ' class=" not_expand" title="Перейти к анализу показателя">' + innerItem.name + '</div><div class="not_expand">' + innerItem.updateDate + '</div><div class="not_expand">' + innerItem.value + '</div><div class="not_expand">' + innerItem.unitName + '</div></div>');
							factGroupInnerContainer.append(innerRow);
						}

						indicatorClone.append(factGroupInnerContainer);
					}
				} else {

					// цикл по вложенным элементам
					for (var l = 0; l < item.facts.length; l++) {
						indicator = item.facts[l];
						indicatorRow = $('<div class="indicator_row indicator_check_box not_expand"><div class="select_indicator_row__div"><input fact-id=' + item.facts[l].id + ' index=' + i + ' fact-index=' + l + ' class=" not_expand" type="checkbox"></div><div index=' + i + ' fact-index=' + l + ' class=" not_expand" title="Перейти к анализу показателя">' + indicator.name + '</div><div class="not_expand">' + indicator.updateDate + '</div><div class="not_expand">' + indicator.value + '</div><div class="not_expand">' + indicator.unitName + '</div></div>');

						indicatorClone.append(indicatorRow);
					}
				}

				indicatorClone.attr('index', i);
				contentBlock.append(indicatorClone);
			}

			existTableContent.next().remove();
			existTableContent.after(contentBlock);

			mainThis.factRows = $('.indicator_row');
			mainThis.cubeRows = $('.indicators_group_row');
			mainThis.dimensionData = facts;
			mainThis.updateShowCount();
			mainThis.updateSelectedCount();
			mainThis.filterShowAll();
			Loader.off();

		});

	},

	openReport: function (event, id, isOwner, reportName, updateDate) {

		var reportNameElemement = document.createElement("div");
		reportNameElemement.setAttribute("id", "save_report_name");
		reportNameElemement.innerHTML = reportName ? reportName : "Анализ показателей";
		// $(".workspace_results_titles")[0].append(reportNameElemement);


		// "<div id='save_report_name'></div>" / //todo ыводить название отчета

		var parentThis = this;
		if (id === null || id === undefined) {
			event.preventDefault();
			var reportId = +event.target.getAttribute("report-id");
		} else {
			reportId = id;
		}
		Loader.on();
		SchemaConfigGlobal.reportId = reportId;
		SchemaConfigGlobal.reportName = reportName ? reportName : "Анализ показателей";
		SchemaConfigGlobal.isOwner = isOwner;
		$.get("saiku/rest/api/scheme/report/" + reportId).then(function (res) {
			globalWorkspace.refresh();
			setTimeout(function () {
				parentThis.header.find(".tabs ul li:nth-child(2) > a").click();  //Переходим на вкладку с анализом
				$("#cubesselect").find("optgroup option").attr('selected', 'true'); //Выбираем схему
				globalWorkspace.new_query();  //Обновляем схему
				$(".go_back_to_reports").addClass("shown"); //Показывем заголовок с кнопкой "Назад"
				$("#tab_panel").addClass("fix_height_by75");

				switch (res.placeId) {
					case 1: {
						globalWorkspace.query.helper.model().queryModel.details.axis = "ROWS";
						globalWorkspace.query.helper.model().queryModel.details.location = "TOP";
						break;
					}
					case 2: {
						globalWorkspace.query.helper.model().queryModel.details.axis = "ROWS";
						globalWorkspace.query.helper.model().queryModel.details.location = "BOTTOM";
						break;
					}
					case 3: {
						globalWorkspace.query.helper.model().queryModel.details.axis = "COLUMNS";
						globalWorkspace.query.helper.model().queryModel.details.location = "TOP";
						break;
					}
					case 4: {
						globalWorkspace.query.helper.model().queryModel.details.axis = "COLUMNS";
						globalWorkspace.query.helper.model().queryModel.details.location = "BOTTOM";
						break;
					}
				}


				$("#headerNavigation").addClass("shown"); //Показывем заголовок с кнопкой "Назад"
				// $("#tab_panel").addClass("round_tab");
				var dimensions = $('.sidebar_inner.dimension_tree .parent_dimension > a');
				if (dimensions.length > 0) {

					dimensions.click(); //разворачиваем все измерения

					//выбираем все показатели и устанавливаем итоги по показателям
					var measures = res.reportMeasures;
					for (var i = 0; i < measures.length; i++) {
						$('.measure_tree ul li.d_measure a').each(function (index, elem) {
							if ($(elem).attr("measure").split("_")[1] === (measures[i].measureId + "")) {
								$(elem).click();
							}
						});
					}

					var modelMeasures = globalWorkspace.query.model.queryModel.details.measures;
					for (var i = 0; i < measures.length; i++) {
						var measure = measures[i];
						if (measure.totalRow != null) {
							if (modelMeasures[i].aggregators) {
								modelMeasures[i].aggregators.push(measure.totalRow + "_ROWS")
							}
							modelMeasures[i].aggregators = [measure.totalRow + "_ROWS"];
							globalWorkspace.query.model.queryModel.axes.ROWS.aggregators = ["nil"];
						}
						if (measure.totalColumn != null) {
							if (modelMeasures[i].aggregators) {
								modelMeasures[i].aggregators.push(measure.totalColumn + "_COLUMNS")
							}
							modelMeasures[i].aggregators = [measure.totalColumn + "_COLUMNS"];
							globalWorkspace.query.model.queryModel.axes.COLUMNS.aggregators = ["nil"];
						}
						if (typeof modelMeasures[i] !== 'undefined') {
							modelMeasures[i].uniqueName = "[Measures].[" + modelMeasures[i].name + "]";
						}
					}

					var sorts = res.reportSorts;
					sorts.forEach(function (sort) {
						var type = sort.sortType;
						if (sort.sortMeasure !== null) {
							globalWorkspace.query.model.queryModel.axes[type].sortEvaluationLiteral = sort.sortMeasure;
							globalWorkspace.query.model.queryModel.axes[type].sortOrder = sort.sortOrder;
						}
						if (sort.totalKey) {
							globalWorkspace.query.model.queryModel.axes[type].aggregators = [sort.totalKey];
						}
						if (sort.intervalTypeId) {
							var expressions = [];
							expressions.push(sort.intervalCount);
							if (sort.intervalParam !== null) {
								expressions.push(sort.intervalParam);
							}
							var filters = [{
								"expressions": expressions,
								"function": sort.intervalTypeId,
								"flavour": "N",
								"operator": null
							}];
							globalWorkspace.query.model.queryModel.axes[type].filters = filters;
						}
					});

					for (var i = 0; i < res.reportDimensions.length; i++) {
						var dim = res.reportDimensions[i];

						$('.dimension_tree ul li .level').each(function (index, elem) {
							var id = $(elem).attr("level").split("_")[1];
							var dimType = $(elem).attr("level").split("_")[0];
							if (dimType === "dimension") {
								if (id === (dim.dimensionId + "")) {
									parentThis.select_dimension($(elem), dim.placeId);
								}
							} else if (dimType === "hierarchy") {
								var dimId = $(elem).attr("hierarchy").split("].[")[0];
								dimId = dimId.split("_")[1];
								if ((dimId === (dim.dimensionId + "")) && ((id === (dim.levelId + "")) || (id === (dim.dtLevelId + "")))) {
									parentThis.select_dimension($(elem), dim.placeId);
								}
							}
						});

						if (dim.dimensionValues.length > 0) {

							var dimValues = dim.dimensionValues;
							var members = [];
							var type = "INCLUSION";
							for (var j = 0; j < dimValues.length; j++) {
								var member = {
									caption: "",
									uniqueName: "[dimension_" + dim.dimensionId + "].[dimension_" + dim.dimensionId + "]." + dimValues[j].valueText
								};
								members.push(member);
							}
							if (dimValues.length > 0) {
								if (dimValues[0].isInclude !== 1) {
									type = "EXCLUSION"
								}
							}
							if ((dim.levelId === null || dim.levelId === 0) && (dim.dtLevelId === null || dim.dtLevelId === 0)) {
								var id = dim.dimensionId;
								globalWorkspace.query.helper.getHierarchy("[dimension_" + id + "].[dimension_" + id + "]").levels["dimension_" + id].selection = {
									"type": type,
									"members": members
								}
							} else if (dim.dtLevelId !== null && dim.dtLevelId !== 0) {
								var id = dim.dimensionId;
								var levelId = dim.dtLevelId;
								globalWorkspace.query.helper.getHierarchy("[dimension_2].[dimension_2]").levels["hierarchy_" + levelId].selection = {
									"type": type,
									"members": members
								}
							} else {
								var id = dim.dimensionId;
								var levelId = dim.levelId;
								globalWorkspace.query.helper.getHierarchy("[dimension_" + id + "].[dimension_" + id + "]").levels["hierarchy_" + levelId].selection = {
									"type": type,
									"members": members
								}
							}
						}

					}

					globalWorkspace.query.run(true);
					var view = res.view;
					if (view && (view.viewId !== 1)) {
						$("#chart_icon").click();
						$("." + view.viewKey).click();
					}
					$(".workspace_results_titles")[0].append(reportNameElemement);
					if (reportName) {
						$("#update_report_name_span")[0].innerHTML = reportName;

						// $(".save_opened_alien_report .report_name_input")[0].value = reportName;
						$(".save_opened_alien_report .report_name_input")[0].value = $("#save_report_name")[0].innerHTML;
					}
					if (updateDate) {
						$("#update_report_update_date_span")[0].innerHTML = updateDate;

					}
					parentThis.addSomeFunctions();
					Loader.off();
				}

			}, 4000);

		});


	},

	select_dimension: function (target, place) {

		var hierarchy = $(target).attr('hierarchy');
		var hierarchyCaption = $(target).parent().parent().attr('hierarchycaption');
		var level = $(target).attr('level');
		var axisName = (place === 1 ? "ROWS" : "COLUMNS");
		switch (place) {
			case 1 : {
				axisName = "ROWS";
				break;
			}
			case 2 : {
				axisName = "COLUMNS";
				break;
			}
			case 7 : {
				axisName = "FILTER";
				break
			}
		}
		var isCalcMember = $(target).parent().hasClass('dimension-level-calcmember');


		if (isCalcMember) {
			var uniqueName = $(event.target).attr('uniquename');
			globalWorkspace.toolbar.$el.find('.group_parents').removeClass('on');
			globalWorkspace.toolbar.group_parents();
			globalWorkspace.query.helper.includeLevelCalculatedMember(axisName, hierarchy, level, uniqueName);
		}
		else {
			globalWorkspace.query.helper.includeLevel(axisName, hierarchy, level);
		}

		// Trigger event when select dimension
		Saiku.session.trigger('dimensionList:select_dimension', {workspace: globalWorkspace});

		globalWorkspace.sync_query();

		return false;
	},

	renderReports: function () {

		var mainThis = this;
		return new Promise(function (resolve) {
			try {
				mainThis.getReports().then(function (reportsData) {
					var reportBlock = $("#report_list_section");
					reportBlock.childNodes = [];
					mainThis.reportsData = reportsData;
					reportBlock.html(_.template(mainThis.reportTemplate)({
						reports: reportsData
					}));
					resolve(true);
				});
			} catch (e) {
			}
		})

	},


	load_schema: function (dontChangeTab) {
		var loadSchemaThis = this;
		Loader.on();
		return new Promise(function (resolve) {
			loadSchemaThis.template().then(function (template) {


				$(loadSchemaThis.el).html(template);

				loadSchemaThis.filterShowAllButton = $('#filterShowAll');
				loadSchemaThis.filterShowSelectedButton = $('#filterShowSelected');
				loadSchemaThis.filterShowUnselectedButton = $('#filterShowUnselected');

				loadSchemaThis.factRows = $('.indicator_row');
				loadSchemaThis.cubeRows = $('.indicators_group_row');
				loadSchemaThis.reportRows = $('.report_row');
				loadSchemaThis.reportIndicatorsRows = $('.report_row_indicator');
				loadSchemaThis.updateJQSelectors();
				$('#showMyReports').click();
				if (!dontChangeTab) {
					loadSchemaThis.header.find(".tabs ul li:nth-child(2) > a").click();
					loadSchemaThis.header.find(".tabs ul li:nth-child(1) > a").click(); //todo вернуть
				}
				var firstRow = $('.indicators_table .indicators_group_row');
				if (firstRow && firstRow[0]) {
					firstRow[0].click();
				}
				$(".report_row:first-child").addClass("selected");
				$(".report_row:first-child").addClass("active");
				if (Saiku.session.roles.includes("ROLE_CONNECT_OTHER_PROGRAMM")) {
					$("#logout_block").css("display", "none")
				} else {
					$("#logout_label").text(Saiku.session.username);
				}

				// loadSchemaThis.expandFirstReport();


				Loader.off();
				resolve();
			});
		});
	},
	expandFirstReport: function () {
		for (var i in this.reportRows) {
			if ((this.reportRows[i] instanceof HTMLDivElement) && this.reportRows[i].getAttribute("is-owner") === "true") {
				this.reportRows[i].click();
				break;
			}
		}
	},
	expandIndicatorRow: function (event) {
		var target = event.target || event;
		if (
			target.className === "indicator_row" ||
			target instanceof HTMLInputElement ||
			target.className.indexOf('not_expand') > -1 ||
			(event.target && event.target.classList.contains("select_all_indicator_row__div"))
		) {
			return
		}
		if (!target.classList.contains("indicators_group_row")) {
			arguments.callee(target.parentElement)
		} else {
			if (target.classList.contains("active")) {
				target.className = target.className.replace("active", "");
			} else {
				target.className = target.className + " active"
			}
		}


		SchemaConfigGlobal.updateShowCount();

	},

	expandReportRow: function (event) {

		var target = event.target || event;
		if (target.className && target.className.length > 0 && target.className.indexOf("report_row") > -1) {
			SchemaConfigGlobal.selectReportRow(target);
		}
		if (target.className === "report_row_indicator" || target instanceof HTMLInputElement || target.className.indexOf('not_expand') > -1) {
			return
		}
		if (!target.classList.contains("report_row")) {
			arguments.callee(target.parentElement)
		} else {
			if (target.classList.contains("active")) {
				target.className = target.className.replace("active", "");
			} else {
				target.className = target.className + " active"
			}
		}


		var shownCount = $('.report_row.active').find('.report_row:not(".hide")').length,
			allCount = $('.report_row').find('.report_row').length;
		var span = document.createElement("span");
		span.innerHTML = "Показано ";
		var showCountElement = $('#indicators_shown_count')[0];
		showCountElement.children = [];
		showCountElement.innerHTML = "";

		showCountElement.appendChild(span);
		showCountElement.innerHTML = showCountElement.innerHTML + shownCount + " из " + allCount;

	},
	selectReportRow: function (target) {
		SchemaConfigGlobal.updateJQSelectors();
		var isSelected = target.className.indexOf("selected") > -1;
		$(this.reportRows).removeClass("selected");
		$(target).addClass("selected");
	},
	// logout: function (target) {
	// 	Saiku.session.logout();
	// }


});


var indicatorsHeaderVm = new Vue({
	el: '#vm_indicators',
	template: "<div id=\"vm_indicators\">\n" +
	"    <div class=\"vm_indicators__caption\">АНАЛИЗ</div>\n" +
	"<div class=\"vm_indicators__select_group\">\n" +
	"    <label>Группировать по</label>\n" +
	"<div class=\"vm_input_icon vm_group_icon\"></div>" +
	"<select v-model=\"selects.group.curSelect\">\n" +
	"    <option v-for=\"(text, key) in selects.group.options\" :value=\"key\">\n" +
	"      {{ text }}\n" +
	"    </option>\n" +
	"  </select>" +
	"</div>\n" +
	"<div class=\"vm_indicators__select_group\">\n" +
	"    <label>Показывать</label>\n" +
	"<div class=\"vm_input_icon vm_show_icon\"></div>" +
	"<select v-model=\"selects.show.curSelect\">\n" +
	"    <option v-for=\"(text, key) in selects.show.options\" :value=\"key\">\n" +
	"      {{ text }}\n" +
	"    </option>\n" +
	"  </select>" +
	"</div>\n" +
	"<div class=\"vm_input_icon vm_search_icon\"></div><input v-model=\"searchInput\" placeholder=\"Найти\" class=\"vm_indicators search_input\">" +
	"<div @click=\"analyze()\" class=\"vm_analysis_button redesigned_button\"><div class=\"vm_analyze_icon\"></div>Анализировать</div>" +
	"</div>",
	data: {
		selects: {
			show: {
				curSelect: 1,
				options: {1: "Все", 2: "Отмеченные", 3: "Неотмеченные"}
			},
			group: {
				curSelect: 1,
				options: {
					1: "Предметная область",
					2: "Единицы измерения",
					// 3: "Приказ",
					// 4: "Управление/отдел",
					0: "Без группировки"
				}
			}
		},
		searchInput: null
	},
	watch: {
		"selects.group.curSelect": function (val) {
			SchemaConfigGlobal.changeGroup(val);
		},
		"selects.show.curSelect": function (val) {
			switch (+val) {
				case 1:
					SchemaConfigGlobal.filterShowAll();
					break;
				case 2:
					SchemaConfigGlobal.filterShowSelected();
					break;
				case 3:
					SchemaConfigGlobal.filterShowUnselected();
					break;
			}
		},
		searchInput: function (val) {
			SchemaConfigGlobal.vSearchByName(val.toLowerCase());


		}
	},
	methods: {
		analyze: function () {
			SchemaConfigGlobal.analysIndicators();
		}
	},
	created: function () {

	}
});


var reportsHeaderVm = new Vue({
	el: '#vm_reports',
	template: "<div class='hide' id=\"vm_reports\">\n" +
	"    <div class=\"vm_reports__caption\">МОИ ОТЧЕТЫ</div>\n" +

	"<div class=\"vm_reports__select_group\">\n" +
	"    <label>Показывать</label>\n" +
	"<div class=\"vm_input_icon vm_show_icon\"></div>" +
	"<select v-model=\"selects.show.curSelect\">\n" +
	"    <option v-for=\"(text, key) in selects.show.options\" :value=\"key\">\n" +
	"      {{ text }}\n" +
	"    </option>\n" +
	"  </select>" +
	"</div>\n" +
	"<div class=\"vm_input_icon vm_search_icon\"></div><input v-model=\"searchInput\" placeholder=\"Найти\" class=\"vm_reports search_input\">" +
	"</div>",
	data: {
		selects: {
			show: {
				curSelect: 1,
				options: {1: "Мои отчеты", 2: "Отчеты других пользователей", 3: "Все отчеты"}
			}
		},
		searchInput: null
	},
	watch: {
		"selects.show.curSelect": function (val) {
			switch (+val) {
				case 1:
					SchemaConfigGlobal.showMyReports();
					break;
				case 2:
					SchemaConfigGlobal.showOtherReports();
					break;
				case 3:
					SchemaConfigGlobal.showAllReports();
					break;
			}
		},
		searchInput: function (val) {
			SchemaConfigGlobal.searchReportByName(val.toLowerCase());


		}
	}
});

var indicatorsTabContent = new Vue({
	el: '#indicators_tab_content',
	template: "<div class=\'hide\' id=\"indicators_tab_content\">\n" +
	"    <div class=\"ind_header\">\n" +
	"        <div class=\"ind_search_icon\"></div>\n" +
	"        <input placeholder=\"Найти индикатор\" class=\"ind_search\">\n" +
	"        <label class=\"ind_header_label\">{{ headerLabelText.toUpperCase() }}</label>\n" +
	"        <div class=\"ind_date_period_block\"><label>Период</label><input type=\"date\"></div>\n" +
	"    </div>\n" +
	"    <div class=\"ind_content\">\n" +
	"        <div class=\"ind_tree\">\n" +
	"            <label class=\"all_inds\">ВСЕ ИНДИКАТОРЫ</label>\n" +
	"            <label :class=\'{active:isFavourite}\' class=\"favourites\" @click=\'showFavourite($event)\'>ИЗБРАННОЕ</label>\n" +
	"            <div class=\"ind_under_fav_line\"></div>\n" +
	"            <div v-for=\'cube in hierarchyData\' class=\"ind_tree_cube\">\n" +
	"                <div class=\"ind_tree_cube__label\" @click=\'onCubeClick($event, cube)\'>\n" +
	"                    {{ cube.nameCube }}\n" +
	"                    <div type=\'expand_arrow\' @click=\'expandCube($event)\'></div>\n" +
	"                </div>\n" +
	"                <div v-for=\'measure in cube.measures\' @click=\'onIndicatorClick($event, measure)\' class=\"ind_tree_ind\">\n" +
	"                    {{ measure.nameMeasure }}\n" +
	"                </div>\n" +
	"            </div>\n" +
	"        </div>\n        " +
	"<div v-if=\'tableData\' v-show=\'isShowTable\' class=\"ind_table\">\n" +
	"            <div class=\"ind_table_content\">\n" +
	"                <div class=\"ind_table_header\">\n" +
	"                    <label>Организация</label>\n" +
	"                    <label>{{ headerLabelText }}</label>\n" +
	"                </div>\n" +
	"                <div class=\'ind_table_rows\'>\n" +
	"                    <div v-for=\"parent in tableData.parents\" class=\"ind_table_row\">\n" +
	"                        <div class=\"ind_table_cube_row\" @click=\'onCubeRowClick($event)\'>\n" +
	"                            <div class=\"ind_table_expand_arrow\"></div>\n" +
	"                            <label class=\"ind_table_cube_name\">{{ parent.name }}</label>\n" +
	"                            <label class=\"ind_table_cube_value\">{{ parent.value }}</label>\n" +
	"                            <div class=\"ind_table_cube_chart\">\n                                " +
	"<div :style=\'{width: getGrbsPercentWidth(parent.value) + \"%\"}\'></div>\n                            " +
	"</div>\n" +
	"                        </div>\n" +
	"                        <div class=\"ind_table_ind\" v-for=\"child in parent.children\">\n" +
	"                            <div class=\"ind_table_ind_row\">\n" +
	"                                <label class=\"ind_table_ind_name\">{{ child.name }}</label>\n" +
	"                                <label class=\"ind_table_ind_value\">{{ child.value }}</label>\n" +
	"                                <div class=\"ind_table_ind_chart\">\n" +
	"                                    <div :style=\'{width: getOrganisationPercentWidth(parent.children, child.value) + \"%\"}\'></div>\n" +
	"                                </div>\n" +
	"                            </div>\n" +
	"                        </div>\n" +
	"                    </div>\n" +
	"                </div>\n" +
	"            </div>\n" +
	"        </div>\n        " +
	"<div v-if=\'cubePanels\' v-show=\'isShowPanels\' class=\'ind_panels_container\'>\n" +
	"            <div v-for=\'panel in cubePanels\' class=\'ind_panel\'>\n" +
	"                <div class=\'ind_panel_header\'>\n" +
	"                    <div class=\'ind_panel_caption\'>\n" +
	"                        <label>{{ panel.name }}</label>\n" +
	"                    </div>\n" +
	"                    <div class=\'ind_panel_favourite_switcher_icon\'></div>\n" +
	"                </div>\n" +
	"                <div class=\'ind_panel_body\'>\n" +
	"                    <!--<div class=\'ind_panel_diagram\'></div>-->\n" +
	"                    <div class=\'ind_panel_indicators\'>\n" +
	"                        <div class=\'ind_panel_indicators_caption\'>ГРБС</div>\n" +
	"                        <template v-for=\'item in panel.tableDataGrbs\'>\n" +
	"                            <div class=\'ind_panel_indicators_item_name\'>{{ item.name }}</div>\n" +
	"                            <div class=\'ind_panel_indicators_item_value\'>{{ item.value }}</div>\n" +
	"                        </template>\n" +
	"                        <div></div>\n" +
	"                        <div class=\'ind_panel_indicators_caption\'>Организации</div>\n" +
	"                        <template v-for=\'item in panel.tableDataOrganization\'>\n" +
	"                            <div class=\'ind_panel_indicators_item_name\'>{{ item.name }}</div>\n" +
	"                            <div class=\'ind_panel_indicators_item_value\'>{{ item.value }}</div>\n" +
	"                        </template>\n" +
	"                    </div>\n" +
	"                </div>\n" +
	"            </div>\n        " +
	"		 </div>\n    " +
	"	</div>\n" +
	"</div>",

	data: {
		indSearchInput: null,
		isFavourite: true,
		isShowPanels: true,
		isShowTable: false,
		headerLabelText: 'Избранное',
		hierarchyData: null,
		tableData: null,
		favouritesData: null,
		cubePanels: null
	},

	mounted: function () {
		this.getHierarchy();
	},

	watch: {

		indSearchInput: function (val) {
			var a = val;


		}
	},
	methods: {
		onCubeRowClick: function (event) {
			// event.target.parentElement.classList.toggle("expanded");
			$(event.target).closest(".ind_table_row")[0].classList.toggle("expanded");
		},
		expandCube: function (event) {
			event.target.parentElement.parentElement.classList.toggle("expanded");
		},
		onCubeClick: function (event, cube) {
			if (event.target.getAttribute("type") === "expand_arrow") return;
			$(".ind_tree div").removeClass("active");
			event.target.classList.add("active");
			this.headerLabelText = event.target.innerText;
			this.getCubePanels(cube.idCube);
		},
		onIndicatorClick: function (event, measure) {
			$(".ind_tree div").removeClass("active");
			event.target.classList.add("active");
			this.headerLabelText = event.target.innerText;
			this.showTable(event, measure);
		},
		showFavourite: function (event) {
			this.headerLabelText = event.target.innerText;
			this.switchIntoFovourites();
		},
		showTable: function (event, measure) {
			Loader.on();
			var vm = this;
			$.get('service/indicator/table', {
				idMeasure: measure.idMeasure,
				beginDate: '', //TODO: сделать date range picker
				endDate: ''//TODO: сделать date range picker
			}).then(function (tableData) {
				Loader.off();
				vm.tableData = tableData;
				vm.switchIntoTable();
			});
		},

		getHierarchy: function () {
			var vm = this;
			$.get('/service/indicator/hierarchy', function (data) {
				vm.hierarchyData = data;
			});
		},

		getGrbsPercentWidth: function (grbsValue) {
			return this.getPercent(this.tableData.parents, grbsValue, function (acc, item) {
				return (item.name !== null && item.value !== null) ? acc + item.value : 0;
			});
		},

		getOrganisationPercentWidth: function (organisations, organisationValue) {
			return this.getPercent(organisations, organisationValue, function (acc, item) {
				return acc + item.value;
			});
		},

		getPercent: function (array, value, accumulator) {
			var reduction = array.reduce(accumulator, 0);
			return (reduction > 0) ? value * 100 / reduction : 0;
		},

		getCubePanels: function (idCube) {
			Loader.on();
			var vm = this;
			$.get('/service/indicator/cubePanels', {
				idCube: idCube
			}).then(function (cubePanels) {
				Loader.off();
				vm.cubePanels = cubePanels;
				vm.switchIntoPanels();
			})
		},

		switchIntoFovourites: function () {
			this.isFavourite = true;
			this.isShowTable = false;
			this.isShowPanels = false;
		},

		switchIntoTable: function () {
			this.isFavourite = false;
			this.isShowTable = true;
			this.isShowPanels = false;
		},

		switchIntoPanels: function () {
			this.isFavourite = false;
			this.isShowTable = false;
			this.isShowPanels = true;
		}

	}
});




