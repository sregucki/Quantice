import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Article} from "../../model/article";

const queryFindArticlesRss = gql`
  query findArticlesRss($keyword: String!) {
    findArticlesRss(keyword: $keyword){url, title, description, publishedAt}
  }
`;

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private apollo: Apollo) {
  }

  public getArticlesRss(keyword: string): Observable<Article[]> {
    return this.apollo.watchQuery<any>({
      query: queryFindArticlesRss,
      variables: {keyword: keyword}
    }).valueChanges.pipe(map(result => result.data.findArticlesRss));
  }

}
