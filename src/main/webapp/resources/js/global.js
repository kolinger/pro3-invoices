function handleClose(xhr, status, args, dialogWidget) {
    if (!args.validationFailed) {
        dialogWidget.hide();
    }
}

/**
 * @link http://stackoverflow.com/a/13917746/3332640
 */
function resizeDialog(dialog) {
    var el = $(dialog.jqId);
    var doc = $('body');
    var win = $(window);
    var elPos = '';
    var bodyHeight = '';
    var bodyWidth = '';
    // position:fixed is maybe cool, but it makes the dialog not scrollable on browser level, even if document is big enough
    if (el.height() > win.height()) {
        bodyHeight = el.height() + 'px';
        elPos = 'absolute';
    }
    if (el.width() > win.width()) {
        bodyWidth = el.width() + 'px';
        elPos = 'absolute';
    }
    el.css('position', 'absolute');
    doc.css('width', bodyWidth);
    doc.css('height', bodyHeight);
    var pos = el.offset();
    if (pos.top + el.height() > doc.height())
        pos.top = doc.height() - el.height();
    if (pos.left + el.width() > doc.width())
        pos.left = doc.width() - el.width();
    var offsetX = 0;
    var offsetY = 0;
    if (elPos != 'absolute') {
        offsetX = $(window).scrollLeft();
        offsetY = $(window).scrollTop();
    }
    // scroll fix for position fixed
    if (pos.left < offsetX)
        pos.left = offsetX;
    if (pos.top < offsetY)
        pos.top = offsetY;
    el.offset(pos);
}