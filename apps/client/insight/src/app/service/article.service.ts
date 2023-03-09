import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Article} from "../model/article";

const queryFindArticlesNewsApi = gql`
  query findArticlesNewsApi($keyword: String!) {
    findArticlesNewsApi(keyword: $keyword){url, title}
  }
`;

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private apollo: Apollo) {
  }

  public getArticlesNewsApi(keyword: string): Observable<Article[]> {
    return this.apollo.watchQuery<any>({
      query: queryFindArticlesNewsApi,
      variables: {keyword: keyword}
    }).valueChanges.pipe(map(result => result.data.findArticlesNewsApi));
  }

}
