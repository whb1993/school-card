import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LibraryInf } from 'app/shared/model/library-inf.model';
import { LibraryInfService } from './library-inf.service';
import { LibraryInfComponent } from './library-inf.component';
import { LibraryInfDetailComponent } from './library-inf-detail.component';
import { LibraryInfUpdateComponent } from './library-inf-update.component';
import { LibraryInfDeletePopupComponent } from './library-inf-delete-dialog.component';
import { ILibraryInf } from 'app/shared/model/library-inf.model';

@Injectable({ providedIn: 'root' })
export class LibraryInfResolve implements Resolve<ILibraryInf> {
    constructor(private service: LibraryInfService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILibraryInf> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LibraryInf>) => response.ok),
                map((libraryInf: HttpResponse<LibraryInf>) => libraryInf.body)
            );
        }
        return of(new LibraryInf());
    }
}

export const libraryInfRoute: Routes = [
    {
        path: '',
        component: LibraryInfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.libraryInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LibraryInfDetailComponent,
        resolve: {
            libraryInf: LibraryInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.libraryInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LibraryInfUpdateComponent,
        resolve: {
            libraryInf: LibraryInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.libraryInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LibraryInfUpdateComponent,
        resolve: {
            libraryInf: LibraryInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.libraryInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const libraryInfPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LibraryInfDeletePopupComponent,
        resolve: {
            libraryInf: LibraryInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.libraryInf.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
