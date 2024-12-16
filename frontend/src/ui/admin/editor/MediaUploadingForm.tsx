import { toast } from 'react-toastify'
import { mediaApi } from '../../../api/api'
import { ChangeEvent, FC, useRef, useState } from 'react'
import { useAuthContext } from '../../../store/auth-context'

interface MediaUploadingFormProps {
    urlPrefix: string
}

const MediaUploadingForm: FC<MediaUploadingFormProps> = (props) => {
    const { token } = useAuthContext()
    const [uploadedFilename, setUploadedFilename] = useState('')

    const handleFileUpload = (event: ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files || event.target.files.length === 0) {
            return
        }

        mediaApi(token)
            .mediaPost({ file: event.target.files[0] })
            .then((response) => {
                toast.success('Uploaded.')

                setUploadedFilename(props.urlPrefix + response.filename)
            })
            .catch(() => toast.error('Failed to upload.'))
    }

    return (
        <div>
            <label htmlFor="filePicker" />
            <input type="file" id="filePicker" onChange={handleFileUpload} />
            {uploadedFilename && (
                <input
                    type="text"
                    value={uploadedFilename}
                    onClick={(e) => {
                        e.currentTarget.select()
                        navigator.clipboard.writeText(e.currentTarget.value)
                    }}
                    readOnly
                />
            )}
        </div>
    )
}

export default MediaUploadingForm
