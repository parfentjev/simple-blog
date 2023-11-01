import NamedContainer from '@/ui/layout/NamedContainer'
import SearchBox from '@/ui/search/SearchBox'
import { FC } from 'react'

const SearchPage: FC<{}> = () => {
  return (
    <NamedContainer name='Search' centered={true}>
      <SearchBox />
    </NamedContainer>
  )
}

export default SearchPage
