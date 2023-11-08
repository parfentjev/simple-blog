import CategoryDto from "../CategoryDto";
import ErrorMessageProvider from "./ErrorMessageProvider";

type GetCategoriesResponse = CategoryDto[] & ErrorMessageProvider

export default GetCategoriesResponse