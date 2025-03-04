import { FC, ReactNode } from 'react'

interface ModalDialogProps {
    visible: boolean
    cancelFn: () => void
    submitFn: () => void
    children: ReactNode
}

const ModalDialog: FC<ModalDialogProps> = (props) => {
    return (
        <>
            <div
                className={`modal-overlay ${
                    (props.visible && 'visible') || ''
                }`}
                onClick={props.cancelFn}
            ></div>
            <div
                className={`modal-dialog ${(props.visible && 'visible') || ''}`}
            >
                <div className="modal-content">{props.children}</div>
                <div className="modal-buttons">
                    <div className="modal-buttons-cancel">
                        <button
                            type="submit"
                            className="secondary-button"
                            onClick={props.cancelFn}
                        >
                            Close
                        </button>
                    </div>
                    <div className="modal-buttons-submit">
                        <button
                            type="submit"
                            className="primary-button"
                            onClick={props.submitFn}
                        >
                            Confirm
                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ModalDialog
