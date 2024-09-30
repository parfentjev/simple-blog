import { FC } from 'react'
import { Link } from 'react-router-dom'

const Footer: FC = () => {
    return (
        <footer>
            <Link to={'mailto:aleksei.parfentjev@proton.me'}>contact</Link>
            <Link to={'/license'} rel="license">
                license
            </Link>
            <Link
                to={'https://codeberg.org/parfentjev/simple-blog'}
                target="_blank"
                rel="noreferrer"
            >
                source
            </Link>
        </footer>
    )
}

export default Footer
