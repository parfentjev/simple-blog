import { FC } from 'react'
import './SeasonalImages.css'

interface SeasonalImagesProps {
    filename: string
    alt: string
    totalFiles: number
}

const SeasonalImages: FC<SeasonalImagesProps> = (props) => {
    const index = Math.floor(Math.random() * props.totalFiles) + 1

    return (
        <div className="seasonal">
            <img
                src={props.filename.replace('%index', index.toString())}
                alt={props.alt}
            />
        </div>
    )
}

export default SeasonalImages
