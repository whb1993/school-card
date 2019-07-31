import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SchoolCardSharedModule } from 'app/shared';
import {
    LostInfComponent,
    LostInfDetailComponent,
    LostInfUpdateComponent,
    LostInfDeletePopupComponent,
    LostInfDeleteDialogComponent,
    lostInfRoute,
    lostInfPopupRoute
} from './';

const ENTITY_STATES = [...lostInfRoute, ...lostInfPopupRoute];

@NgModule({
    imports: [SchoolCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LostInfComponent,
        LostInfDetailComponent,
        LostInfUpdateComponent,
        LostInfDeleteDialogComponent,
        LostInfDeletePopupComponent
    ],
    entryComponents: [LostInfComponent, LostInfUpdateComponent, LostInfDeleteDialogComponent, LostInfDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolCardLostInfModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
