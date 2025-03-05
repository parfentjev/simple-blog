import { FC } from 'react'
import { Link } from 'react-router'

const Footer: FC = () => {
    return (
        <footer>
            <div>
                <Link to={'mailto:aleksei@fakeplastictrees.ee'}>contact</Link>
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
            </div>
            <div>
                <Link to={'/banner'}>
                    <img src="/images/retro/banner.png" alt="Banner" />
                </Link>
            </div>
        </footer>
    )
}

export default Footer
