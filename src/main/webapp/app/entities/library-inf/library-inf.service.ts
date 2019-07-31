import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILibraryInf } from 'app/shared/model/library-inf.model';

type EntityResponseType = HttpResponse<ILibraryInf>;
type EntityArrayResponseType = HttpResponse<ILibraryInf[]>;

@Injectable({ providedIn: 'root' })
export class LibraryInfService {
    public resourceUrl = SERVER_API_URL + 'api/library-infs';

    constructor(protected http: HttpClient) {}

    create(libraryInf: ILibraryInf): Observable<EntityResponseType> {
        return this.http.post<ILibraryInf>(this.resourceUrl, libraryInf, { observe: 'response' });
    }

    update(libraryInf: ILibraryInf): Observable<EntityResponseType> {
        return this.http.put<ILibraryInf>(this.resourceUrl, libraryInf, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILibraryInf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILibraryInf[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
