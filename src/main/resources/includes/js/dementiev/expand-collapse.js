function expandCollapse(idOfElement) {
    jQuery(function ($) {
        if ($("#expand-log-href"+idOfElement).hasClass("expand-log-href")) {
            $("#expand-log-href" + idOfElement).removeClass("expand-log-href").addClass("collapse-log-href");
            togglePartsOfLog();
        } else if ($("#expand-log-href"+idOfElement).hasClass("collapse-log-href")) {
            $("#expand-log-href" + idOfElement).removeClass("collapse-log-href").addClass("expand-log-href");
            togglePartsOfLog();
        }

        function togglePartsOfLog() {
            $('#log-infotable' + idOfElement).toggle();
            $('#log-body-collapsed' + idOfElement).toggle();
            $('#log-attachments' + idOfElement).toggle();
        }
    });
}
