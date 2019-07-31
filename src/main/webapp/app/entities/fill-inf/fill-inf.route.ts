import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FillInf } from 'app/shared/model/fill-inf.model';
import { FillInfService } from './fill-inf.service';
import { FillInfComponent } from './fill-inf.component';
import { FillInfDetailComponent } from './fill-inf-detail.component';
import { FillInfUpdateComponent } from './fill-inf-update.component';
import { FillInfDeletePopupComponent } from './fill-inf-delete-dialog.component';
import { IFillInf } from 'app/shared/model/fill-inf.model';

@Injectable({ providedIn: 'root' })
export class FillInfResolve implements Resolve<IFillInf> {
    constructor(private service: FillInfService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFillInf> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FillInf>) => response.ok),
                map((fillInf: HttpResponse<FillInf>) => fillInf.body)
            );
        }
        return of(new FillInf());
    }
}

export const fillInfRoute: Routes = [
    {
        path: '',
        component: FillInfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.fillInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FillInfDetailComponent,
        resolve: {
            fillInf: FillInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.fillInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FillInfUpdateComponent,
        resolve: {
            fillInf: FillInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.fillInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FillInfUpdateComponent,
        resolve: {
            fillInf: FillInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.fillInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fillInfPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FillInfDeletePopupComponent,
        resolve: {
            fillInf: FillInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.fillInf.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
