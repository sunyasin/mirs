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
 * Controls the appearance and behavior of the dimension list
 *
 * This is where drag and drop lives
 */
var DimensionListGlobal;
var DimensionList = Backbone.View.extend({
    events: {
        'click span': 'select',
        'click a': 'select',
        'click .parent_dimension ul li a.level' : 'select_dimension',
        'click .measure' : 'select_measure',
        'click .addMeasure' : 'measure_dialog',

		'click  .select_all_indicator_row__div': 'recheckRow',
		'click  .indicator_check_box': 'recheckIndicator',
		'click  .indicators_group_row': 'expandIndicatorRow',
		'click  #filterShowAll ': 'filterShowAll',
		'click  #filterShowSelected': 'filterShowSelected',
		'click  #clearAll': 'clearAll',
		'click  #filterShowUnselected': 'filterShowUnselected',
		'click  #searchByName': 'searchByName',
		'click  #clearSearch': 'clearSearch',
		'click #addMeasureButton': 'addMeasure'
    },



    initialize: function(args) {
        // Don't lose this
        _.bindAll(this,
			"render",
			"load_dimension",
			"select_dimension",
			"addMeasure"

		);

        // Bind parent element
        this.workspace = args.workspace;
        this.cube = args.cube;
		DimensionListGlobal = this;
    },

    load_dimension: function() {
        this.template = this.cube.get('template_attributes');
        this.render_attributes();
        this.workspace.sync_query();

    },

    render: function() {
         // Fetch from the server if we haven't already
        if (this.cube && this.cube.has('template_attributes')) {
            this.load_dimension();
        } else if (! this.cube){
            $(this.el).html('Could not load attributes. Please log out and log in again.');
        } else {
            var template = _.template($("#template-attributes").html());
            $(this.el).html(template);
            $(this.el).find(".loading").removeClass("hide");
            this.workspace.bind('cube:loaded',  this.load_dimension);

        }




        return this;
    },

    render_attributes: function() {
        // Pull the HTML from cache and hide all dimensions
        var self = this;
        $(this.el).html(this.template);
        if (isIE && isIE <= 8) {
            $(this.el).show();
        } else {
            $(this.el).fadeTo(500,1);
        }

        // Add draggable behavior
        $(this.el).find('.addMeasure, .calculated_measures').show();
        $(this.el).find('.measure').parent('li').draggable({
            cancel: '.not-draggable',
            connectToSortable: $(this.workspace.el).find('.fields_list_body.details ul.connectable'),
            helper: 'clone',
            placeholder: 'placeholder',
            opacity: 0.60,
            tolerance: 'touch',
            containment:    $(self.workspace.el),
            cursorAt: { top: 10, left: 35 }
        });

        $(this.el).find('.level').parent('li').draggable({
            cancel: '.not-draggable, .hierarchy',
            connectToSortable: $(this.workspace.el).find('.fields_list_body.columns > ul.connectable, .fields_list_body.rows > ul.connectable, .fields_list_body.filter > ul.connectable'),
            containment:    $(self.workspace.el),
            //helper: "clone",
            helper: function(event, ui){
                var target = $(event.target).hasClass('d_level') ? $(event.target) : $(event.target).parent();
                var hierarchy = target.find('a').attr('hierarchy');
                var level = target.find('a').attr('level');
                var h = target.parent().clone().removeClass('d_hierarchy').addClass('hierarchy');
                h.find('li a[hierarchy="' + hierarchy + '"]').parent().hide();
                h.find('li a[level="' + level + '"]').parent().show();
                var selection = $('<li class="selection"></li>');
                selection.append(h);
                return selection;

            },

            placeholder: 'placeholder',
            opacity: 0.60,
            tolerance: 'touch',
            cursorAt: {
                top: 10,
                left: 85
            }
        });
    },

    indicatorsTemplate: "<div id='addNewMeasureInAnalys' class='full_page_form indicators_form'><div class='add_measure_header'>ДОБАВЛЕНИЕ ПОКАЗАТЕЛЕЙ ДЛЯ АНАЛИЗА</div>" +
	"<div class='indicators_header'>" +
	"<input place-holder='Поиск' class='indicators_header__search_box'>" +
	"<div id='searchByName' class='indicators_header__search_button' ></div>" +
	"<div id='clearSearch'class='indicators_header__clear_search_button hide' ></div>" +

	"</div>" +
	"<div class='indicators_table'>" +
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
	"</div>" +
	"<% }) %>" +
	"</div>" +
	"<% }) %>" +
	"</div>" +
	"<div class='bottom_panel'>	" +
	"<div id='indicators_shown_count'>Показано 0 из <%= allFactsCount %> </span></div>" +
	"<div id='indicators_selected_count'><span>Выбрано </span> 0</div>" +
	"</div></div><div class='bottom_line'></div><button class='img_into_button add_measure_button redesigned_button' id='addMeasureButton'>Добавить в выборку</button><div class='img_into_button cancel_add_measure_button' id='cancelAddMeasureButton'>Отмена</div></div></div>",

    select: function(event) {
        var $target = $(event.target).hasClass('root') ? $(event.target) : $(event.target).parent().find('span');
        if ($target.hasClass('root')) {
            $target.find('a').toggleClass('folder_collapsed').toggleClass('folder_expand');
            $target.toggleClass('collapsed').toggleClass('expand');
            $target.parents('li').find('ul').children('li').toggle();

            if($target.hasClass('expand')){
                Saiku.events.trigger("workspace:expandDimension", this, null);

            }
        }

        return false;
    },

     select_dimension: function(event, ui) {
        if (this.workspace.query.model.type != "QUERYMODEL") {
            return;
        }
        if ($(event.target).parent().hasClass('ui-state-disabled')) {
            event.preventDefault();
            event.stopPropagation();
            return;
        }

        var hierarchy = $(event.target).attr('hierarchy');
        var hierarchyCaption = $(event.target).parent().parent().attr('hierarchycaption');
        var level = $(event.target).attr('level');
        var axisName = "ROWS";
        var isCalcMember = $(event.target).parent().hasClass('dimension-level-calcmember');

        if ($(this.workspace.el).find(".workspace_fields ul.hierarchy[hierarchy='" + hierarchy + "']").length > 0) {
             var $level = $(this.workspace.el).find(".workspace_fields ul[hierarchy='" + hierarchy + "'] a[level='" + level + "']").parent().show();
            axisName = $level.parents('.fields_list_body').hasClass('rows') ? "ROWS" : "COLUMNS";
        } else {
            var $axis = $(this.workspace.el).find(".workspace_fields .fields_list[title='ROWS'] ul.hierarchy").length > 0 ?
                $(this.workspace.el).find(".workspace_fields .fields_list[title='COLUMNS'] ul.connectable") :
                $(this.workspace.el).find(".workspace_fields .fields_list[title='ROWS'] ul.connectable") ;

            axisName = $axis.parents('.fields_list').attr('title');
        }

        if (isCalcMember) {
            var uniqueName = $(event.target).attr('uniquename');
            this.workspace.toolbar.$el.find('.group_parents').removeClass('on');
            this.workspace.toolbar.group_parents();
            this.workspace.query.helper.includeLevelCalculatedMember(axisName, hierarchy, level, uniqueName);
        }
        else {
            this.workspace.query.helper.includeLevel(axisName, hierarchy, level);
        }

        // Trigger event when select dimension
        Saiku.session.trigger('dimensionList:select_dimension', { workspace: this.workspace });

        this.workspace.sync_query();
        this.workspace.query.run();
        event.preventDefault();
        return false;
    },

    select_measure: function(event, ui) {
        if ($(event.target).parent().hasClass('ui-state-disabled')) {
            return;
        }
        var $target = $(event.target).parent().clone();
        var measure = {
            "name": $target.find('a').attr('measure'),
            "type": $target.find('a').attr('type')
        };
        this.workspace.query.helper.includeMeasure(measure);
        this.workspace.sync_query();
        this.workspace.query.run();
        event.preventDefault();
        return false;
    },

    // measure_dialog: function(event, ui) {
    //     (new CalculatedMemberModal({
    //         workspace: this.workspace,
    //         measure: null
    //     })).render().open();
    // }

	measure_dialog: function(event, ui) {
    	var addMeasureDialog = $(".add_measure_modal_dialog");
		addMeasureDialog.removeClass("hide");

		SchemaConfigGlobal.dimensionDataForAdding.forEach(function (item) {
			if (item.factGroups !== null) {

				item.factGroups.forEach(function (innerGroup) {
					innerGroup.facts.forEach( function (fact) {
						fact.isSelected=false;

					});
				});

			}else {
				item.facts.forEach(function (fact) {
					fact.isSelected=false;
				})
			}
		});
		addMeasureDialog.html(_.template(this.indicatorsTemplate)({
			measureGroups: SchemaConfigGlobal.dimensionDataForAdding,
			allFactsCount: SchemaConfigGlobal.allFactsCount
		}));

		var parent = $("#addNewMeasureInAnalys").find(".indicators_table")[0];
		parent.parentElement.insertBefore(addIndicatorHeader.$el, parent.parentElement.firstChild);

		//hide already selected measures
    	var indicatorRows = addMeasureDialog.find('.indicator_row');
		var indicatorRowGroups = addMeasureDialog.find('.indicators_group_row');
		indicatorRows.removeClass('hidden');
		indicatorRowGroups.addClass('hidden');
		var ids = [];
		$(".measure_tree a").each(function (index, item) {
			var id = $(item).attr("measure").split("_")[1];
			ids.push(id);
		});
		indicatorRows.each(function (){
			var measureIndex = $(this).find('input[fact-id]').attr('fact-id');
			if ($.inArray(measureIndex, ids) !== -1){
				$(this).addClass('hidden');
			}
		});
		indicatorRowGroups.each(function (){
			if ($(this).find('.indicator_row:not(.hidden)').length){
				$(this).removeClass('hidden');
			}
		});
	},
	addMeasure: function(newIds){
		// var ids = [22, 58];
		// var autoUpdate = $('#automatic_icon');
		// autoUpdate.find('input')[0].checked = !autoUpdate.find('input')[0].checked;
		var ids = [];
		$(".measure_tree a").each(function (index, item) {
			var id = $(item).attr("measure").split("_")[1];
			ids.push(id);
		});
		ids = ids.concat(newIds);
		ids=ids.join(",");
		Loader.on();

		var helper = this.workspace.query.helper;
		var queryModel = helper.model().queryModel;
		var details = queryModel.details;
		var measures = details.measures;

		var placeId;
		if (details.axis === "COLUMNS") {
			if (details.location === "TOP") {
				placeId = 3;
			} else {
				placeId = 4;
			}
		} else {
			if (details.location === "TOP") {
				placeId = 1;
			} else {
				placeId = 2;
			}
		}

		var reportMeasures = [];
		for (var i = 0; i < measures.length; i++) {
			var aggregators = measures[i].aggregators;
			var totalRow = null;
			var totalColumn = null;
			if (aggregators) {
				aggregators.forEach(function (agg) {
					var aggName = agg.split("_")[0];
					var aggType = agg.split("_")[1];
					if (aggType === "ROWS") {
						totalRow = aggName;
					}
					if (aggType === "COLUMNS") {
						totalColumn = aggName;
					}
				})
			}
			reportMeasures.push(
				{
					"measureId" : measures[i].name.split("_")[1],
					"placeOrder" : i + 1,
					"totalRow" : totalRow,
					"totalColumn" : totalColumn
				}
			)
		}

		var reportDimensions = [];
		var reportSorts = [];
		var axes = ["COLUMNS", "ROWS", "FILTER"];
		for (var j=0; j < axes.length; j++) {
			var axe = axes[j];
			var hierarchies = queryModel.axes[axe].hierarchies;
			var placeDimId;
			switch (axe) {
				case "COLUMNS" : {
					placeDimId = 2;
					break;
				}
				case "ROWS" : {
					placeDimId = 1;
					break;
				}
				case "FILTER" : {
					placeDimId = 7;
					break;
				}
			}
			for (var i =0; i < hierarchies.length; i++) {
				var hierarchy = hierarchies[i];
				var dimName = hierarchy.dimension;
				if (dimName) {
					var dimId = dimName.split("_")[1];
				}
				var properties = Object.getOwnPropertyNames(hierarchy.levels);
				for(var n = 0; n < properties.length; n++) {
					var property = Object.getOwnPropertyNames(hierarchy.levels)[n];
					var level = hierarchy.levels[property];
					var levelId = null;
					var dtLevelId = null;
					if (property !== dimName) {
						if (dimId === "2") {
							dtLevelId = property.split("_")[1];
						} else {
							levelId = property.split("_")[1];
						}
					}
					var dimValues = [];
					var members = level.selection.members;
					var type = (level.selection.type === "INCLUSION") ? 1 : 0;
					for (var k = 0; k < members.length; k++) {
						var member = members[k];
						if (member) {
							var valueText = member.uniqueName.split(".").slice(2).join(".");
							dimValues.push({
								"valueText": valueText,
								"isInclude": type
							})
						}
					}
					var reportDim = {
						"dimensionId": dimId,
						"placeId": placeDimId,
						"placeOrder": i + 1,
						"levelId": levelId,
						"dtLevelId": dtLevelId,
						"dimensionValues": dimValues
					};
					reportDimensions.push(reportDim);
				}
			}
			var axeModel = queryModel.axes[axe];
			if (axeModel.sortEvaluationLiteral !== null || axeModel.aggregators.length > 0 || (axeModel.filters && axeModel.filters.length > 0)) {
				var intervalTypeId = null;
				var intervalCount = null;
				var intervalParam = null;
				if (axeModel.filters.length > 0) {
					var filters = axeModel.filters[0];
					intervalTypeId = filters.function;
					var expressions = filters.expressions;
					intervalCount = expressions[0];
					if (expressions.length > 1) {
						intervalParam = expressions[1];
					}
				}
				var reportSort = {
					"totalKey" : ((axeModel.aggregators.length > 0) ? axeModel.aggregators.join(",") : null),
					"sortType" : axe,
					"sortOrder" : axeModel.sortOrder,
					"sortMeasure" : axeModel.sortEvaluationLiteral,
					"intervalCount" : intervalCount,
					"intervalTypeId" : intervalTypeId,
					"intervalParam" : intervalParam
				};
				reportSorts.push(reportSort);
			}
		}

		var viewSelector = null;
		if ($("#chart_icon").hasClass("on")) {
			viewSelector = $(".chartoption.on").attr("href").substring(1);
		}

		var model = {
			"viewSelector" : viewSelector,
			"placeMeasureId": placeId,
			"reportDimensions" : reportDimensions,
			"reportMeasures" : reportMeasures,
			"reportSorts" : reportSorts
		};

		var workspace = this.workspace;
		//Удаляем все диалоговые окна чтобы не нажать случайно лишнего
		$(".ui-dialog").remove();
		$.get("saiku/rest/api/scheme/"+Saiku.session.username+"/"+Saiku.session.sessionid+"/"+ids).then(function (res) {
			workspace.refresh();
			setTimeout(function () {
				$("#cubesselect").find("optgroup option").attr('selected', 'true'); //Выбираем схему
				workspace.new_query();  //Обновляем схему
				//Нажимаем кнопку ОК на вопрос об обновлении запроса
				$($($(".dialog_footer").get(0)).find("a")[0]).click();

				switch(model.placeMeasureId) {
					case 1: {
						workspace.query.helper.model().queryModel.details.axis = "ROWS";
						workspace.query.helper.model().queryModel.details.location = "TOP";
						break;
					}
					case 2: {
						workspace.query.helper.model().queryModel.details.axis = "ROWS";
						workspace.query.helper.model().queryModel.details.location = "BOTTOM";
						break;
					}
					case 3: {
						workspace.query.helper.model().queryModel.details.axis = "COLUMNS";
						workspace.query.helper.model().queryModel.details.location = "TOP";
						break;
					}
					case 4: {
						workspace.query.helper.model().queryModel.details.axis = "COLUMNS";
						workspace.query.helper.model().queryModel.details.location = "BOTTOM";
						break;
					}
				}

				var dimensions = $('.sidebar_inner.dimension_tree .parent_dimension > a');
				if (dimensions.length > 0) {

					dimensions.click(); //разворачиваем все измерения

					//выбираем все показатели
					var measures = model.reportMeasures;
					for (var i = 0; i < measures.length; i++) {
						$('.measure_tree ul li.d_measure a').each(function(index, elem){
							if ($(elem).attr("measure").split("_")[1] === (measures[i].measureId+"")) {
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
						modelMeasures[i].uniqueName = "[Measures].[" + modelMeasures[i].name + "]";
					}

					var sorts = model.reportSorts;
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
								"expressions" : expressions,
								"function" : sort.intervalTypeId,
								"flavour" : "N",
								"operator" : null
							}];
							globalWorkspace.query.model.queryModel.axes[type].filters = filters;
						}
					});

					for (var i = 0; i < model.reportDimensions.length; i++) {
						var dim = model.reportDimensions[i];

						$('.dimension_tree ul li .level').each(function(index, elem) {
							var id = $(elem).attr("level").split("_")[1];
							var dimType = $(elem).attr("level").split("_")[0];
							if (dimType === "dimension") {
								if (id === (dim.dimensionId+"")) {
									SchemaConfigGlobal.select_dimension($(elem), dim.placeId);
								}
							} else if (dimType === "hierarchy") {
								var dimId = $(elem).attr("hierarchy").split("].[")[0]
								dimId = dimId.split("_")[1]
								if ((dimId === (dim.dimensionId+"")) && ((id === (dim.levelId+"")) || (id === (dim.dtLevelId+"")))) {
									SchemaConfigGlobal.select_dimension($(elem), dim.placeId);
								}
							}
						});

						if (dim.dimensionValues.length > 0) {

							var dimValues = dim.dimensionValues;
							var members = [];
							var type = "INCLUSION";
							for (var j=0; j < dimValues.length; j++) {
								var member = {caption: "", uniqueName: "[dimension_"+dim.dimensionId+"].[dimension_"+dim.dimensionId+"]."+dimValues[j].valueText};
								members.push(member);
							}
							if (dimValues.length > 0) {
								if (dimValues[0].isInclude !== 1) {
									type = "EXCLUSION"
								}
							}
							if (dim.levelId === null && dim.dtLevelId === null) {
								var id = dim.dimensionId;
								workspace.query.helper.getHierarchy("[dimension_"+id+"].[dimension_"+id+"]").levels["dimension_"+id].selection = { "type": type, "members": members}
							} else if (dim.dtLevelId !== null) {
								var id = dim.dimensionId;
								var levelId = dim.dtLevelId;
								workspace.query.helper.getHierarchy("[dimension_2].[dimension_2]").levels["hierarchy_"+levelId].selection = { "type": type, "members": members}
							} else {
								var id = dim.dimensionId;
								var levelId = dim.levelId;
								workspace.query.helper.getHierarchy("[dimension_"+id+"].[dimension_"+id+"]").levels["hierarchy_"+levelId].selection = { "type": type, "members": members}
							}
						}

					}

					workspace.query.run(true);
					var viewSelector = model.viewSelector;
					if (viewSelector) {
						$("#chart_icon").click();
						$("." + viewSelector).click();
					}


					Loader.off();
					$(".add_measure_modal_dialog").addClass("hide");
					$("#addNewMeasureInAnalys").remove();
				}

			},4000);
		});
	}
});


var addIndicatorHeader = new Vue({
	el: '#vm_add_indicator_header',
	template: "<div class=\"add_measures__indicators_table__header\"><div @click=\"selectAll()\"class=\"select_all_groups__in_adding_indicators\">" +
	"<input type=\"checkbox\"></div><div>Группа</div><div class=\"icon_and_input_block\">" +
	"<div class=\"vm_input_icon vm_search_icon\"></div>" +
	"<input v-model=\"addMeasureSearchInput\"  placeholder=\"Найти\" class=\"vm_reports search_input\"></div></div>",


	data: {
		addMeasureSearchInput: null
	},
	watch: {
		addMeasureSearchInput: function (val) {
			SchemaConfigGlobal.vSearchByName(val.toLowerCase(),SchemaConfigGlobal.dimensionDataForAdding);


		}
	},
	methods:{
		selectAll:function () {
			$("#addNewMeasureInAnalys").find(".select_all_indicator_row").click()
		}
	}
});
