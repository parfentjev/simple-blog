import Link from 'next/link'
import { FC } from 'react'

const LicensePage: FC = () => {
  return (
    <>
      <p>
        Posts on this website are licensed under the Creative Commons
        Attribution 4.0 International License. To view a copy of this license,
        visit{' '}
        <Link
          href='http://creativecommons.org/licenses/by/4.0/'
          target='_blank'
        >
          http://creativecommons.org/licenses/by/4.0/
        </Link>{' '}
        or send a letter to Creative Commons, PO Box 1866, Mountain View, CA
        94042, USA.
      </p>
      <p>
        However, some photos or quotes can be distributes by their authors under
        different licenseses. I mention original sources in my posts.
      </p>
    </>
  )
}

export default LicensePage
