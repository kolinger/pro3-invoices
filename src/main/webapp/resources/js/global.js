function handleClose(xhr, status, args, dialogWidget) {
    if (!args.validationFailed) {
        dialogWidget.hide();
    }
}
