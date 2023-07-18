package app.mat.movie.presentation.component.dialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.mat.movie.R

@Composable
fun ExitDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {}
) {
    ApplicationDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        infoText = stringResource(
            id = R.string.exit_dialog_info
        ),
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClicked()
                }) {
                Text(
                    text = stringResource(
                        id = R.string.exit_dialog_confirm_button_label
                    )
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancelClicked()
                }
            ) {
                Text(
                    stringResource(
                        R.string.exit_dialog_cancel_button_label
                    ),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    )
}