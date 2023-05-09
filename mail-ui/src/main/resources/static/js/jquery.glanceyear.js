
var provider = document.getElementById("provider").innerText;

$.ajax({
    url: '/notifications/'+provider,
    method: 'GET',
    dataType: 'json',
    success: function (response) {
        var massiveData = Object.entries(response).map(([date, count]) => [new Date(date).toLocaleDateString(), count]);

        $('#js-glanceyear').empty().glanceyear(
            massiveData,
            {
                eventClick: function(e) {
                    $('#debug').html('Date: '+ e.date + ', Count: ' + e.count);
                },
                showToday: false
            }

        );
    },
    error: function (xhr, status, error) {
        console.error('Error fetching data from the backend:', error);
    }
});


(function ($) {
    $.fn.glanceyear = function (massiveData, options) {

        var $_this = $(this);

        var settings = $.extend({
            eventClick: function (e) {
                alert('Date: ' + e.date + ', Count:' + e.count);
            },
            months: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            weeks: ['M', 'T', 'W', 'T', 'F', 'S', 'S'],
            targetQuantity: '.glanceyear-quantity',
            tagId: 'glanceyear-svgTag',
            showToday: false,
            today: new Date()
        }, options);
        var svgElement = createElementSvg('svg', {'width': 54 * 12 + 15, 'height': 7 * 12 + 15});

        var gElementContainer = createElementSvg('g', {'transform': 'translate(15, 15)'});

        var $_tag = $('<div>')
            .addClass('svg-tag')
            .attr('id', settings.tagId)
            .appendTo($('body'))
            .hide();

        var dayCount = 366;
        var monthCount;

        var dateMassive = {};

        massiveData.forEach(([date, count]) => {
            if (date in dateMassive) {
                dateMassive[date] += count;
            } else {
                dateMassive[date] = count;
            }
        });
        //Weeks
        for (var i = 0; i < massiveData.length; i++) {
            var gElement = createElementSvg('g', {'transform': 'translate(' + (12 * i) + ',0)'});

            var d=massiveData[i][0];
            var specificCount =dateMassive[d];

            var dateComponents = d.split(".");
            var day = parseInt(dateComponents[0], 10);
            var month = parseInt(dateComponents[1], 10) - 1;  // Month is zero-based in JavaScript Date objects
            var year = parseInt(dateComponents[2], 10);
            var firstDate = new Date(year, month, day);

            // massiveData.forEach(([date, countValue]) => {
            //     if (date === rectDataDate) {
            //         count = countValue;
            //         return;
            //     }
            // });

            var daysLeft = daysInMonth(firstDate) - firstDate.getDate();

            // Days in week
            for (var j = firstDate.getDay(); j < 7; j++) {

                var rectDate = new Date();
                rectDate.setMonth(settings.today.getMonth());
                rectDate.setFullYear(settings.today.getFullYear());
                rectDate.setDate(settings.today.getDate() - dayCount);

                if (rectDate.getMonth() != monthCount && i < 52 && j > 3 && daysLeft > 7) {
                    //new Month
                    var textMonth = createElementSvg('text', {'x': 12 * i, 'y': '-6', 'class': 'month'});
                    textMonth.textContent = getNameMonth(rectDate.getMonth());
                    gElementContainer.appendChild(textMonth);
                    monthCount = rectDate.getMonth();
                }

                dayCount--;
                if (dayCount >= 0 || (settings.showToday && dayCount >= -1)) {
                    // Day-obj factory

                    var rectElement = createElementSvg('rect', {
                        'class': 'day',
                        'id':specificCount,
                        'width': '10px',
                        'height': '10px',
                        'fill':'green',
                        'data-count':specificCount,
                        'data-date': rectDate.getFullYear() + '-' + (rectDate.getMonth() + 1) + '-' + rectDate.getDate(),
                        'y': 12 * j
                    });
// JavaScript code


                    var rectDate = new Date(rectElement.getAttribute('data-date'));
                    for (var j = 0; j < massiveData.length; j++) {
                        var date = massiveData[j][0];

                        // Compare 'data-date' attribute with the date from 'massiveData'
                        if (date === rectElement.getAttribute('data-date')) {
                            // Set the color of 'rectElement' to a dark color
                            rectElement.setAttribute('fill', 'green');
                            break; // Break out of the loop once a match is found
                        }
                    }

                    rectElement.onmouseover = function () {
                        var dateString = $(this).attr('data-date').split('-');
                        var date = new Date(dateString[0], dateString[1] - 1, dateString[2]);

                        var tagDate = getBeautyDate(date);
                        var tagCount = $(this).attr('data-count');
                        var tagCountData = $(this).attr('data-count');

                        if (specificCount) {
                            if (specificCount >= 1)
                                tagCount = $(this).attr('data-count') + ' scores';
                            else
                                tagCount = $(this).attr('data-count') + ' score';
                        } else {
                            tagCount = 'No scores';
                        }

                        $_tag.html('<b>' + tagCount + '</b> on ' + tagDate)
                            .show()
                            .css({
                                'left': $(this).offset().left - $_tag.outerWidth() / 2 + 5,
                                'top': $(this).offset().top - 33
                            });
                    };

                    rectElement.onmouseleave = function () {
                        $_tag.text('').hide();
                    }

                    rectElement.onclick = function () {
                        settings.eventClick(
                            {
                                date: $(this).attr('data-date'),
                                count: $(this).attr('data-count') || 0
                            }
                        );

                    }

                    gElement.appendChild(rectElement);
                }
            }

            gElementContainer.appendChild(gElement);
        }
        var textM = createElementSvg('text', {'x': '-14', 'y': '8'});
        textM.textContent = getNameWeek(0);
        gElementContainer.appendChild(textM);
        var textW = createElementSvg('text', {'x': '-14', 'y': '32'});
        textW.textContent = getNameWeek(2);
        gElementContainer.appendChild(textW);
        var textF = createElementSvg('text', {'x': '-14', 'y': '56'});
        textF.textContent = getNameWeek(4);
        gElementContainer.appendChild(textF);
        var textS = createElementSvg('text', {'x': '-14', 'y': '80'});
        textS.textContent = getNameWeek(6);
        gElementContainer.appendChild(textS);

        svgElement.appendChild(gElementContainer);

        // Append Calendar to document;
        $_this.append(svgElement);

        fillData(massiveData);


        function createElementSvg(type, prop) {
            var e = document.createElementNS('http://www.w3.org/2000/svg', type);
            for (var p in prop) {
                e.setAttribute(p, prop[p]);
            }
            return e;
        }


        function fillData(massiveData) {
            var scoreCount = 0;
            for (var m in massiveData) {
                $_this.find('rect.day[data-date="' + massiveData[m].date + '"]').attr('data-count', massiveData[m].value);
                scoreCount += parseInt(massiveData[m].value);
            }
            $(settings.targetQuantity).text(massiveData.length + ' days, ' + scoreCount + ' scores');

        }

        function getNameMonth(a) {
            return settings.months[a];
        }

        function getNameWeek(a) {
            return settings.weeks[a];
        }

        function getBeautyDate(a) {
            return getNameMonth(a.getMonth()) + ' ' + a.getDate() + ', ' + a.getFullYear();
        }

        function daysInMonth(d) {

            return 32 - new Date(d.getFullYear(), d.getMonth(), 32).getDate();
        }
        var dayElements = document.getElementsByClassName("day");

        for (var i = 0; i < dayElements.length; i++) {
            var element = dayElements[i];
            var id = element.getAttribute("id");

            if (id > 2) {
                element.style.fill = "green";
            }else if (id < 2) {
                element.style.fill = "red";
            }else{
                element.style.fill = "blue";

            }
        }
    };

})(jQuery);
