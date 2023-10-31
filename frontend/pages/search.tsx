import CenteredContainer from '@/ui/layout/CenteredContainer'
import SearchBox from '@/ui/search/SearchBox'
import { FC } from 'react'

const SearchPage: FC<{}> = () => {
  return (
    <CenteredContainer name='Search'>
      <SearchBox />
    </CenteredContainer>
  )
}

export default SearchPage
