function centroids = decompose(X)
% X = [0 0; 0 20; 10 50; 20 20; 20 0];

pg = polyshape(X(:,1),X(:,2));
tr = triangulation(pg);

plot(pg);
title('Input shape');

pdem = createpde;

pdem.geometryFromMesh(tr.Points', tr.ConnectivityList')
mesh = generateMesh(pdem, 'GeometricOrder', 'linear', 'Hmax', 2, 'Hgrad', 1.01);

% TODO: Preallocate array size
centroids = [];

for elem = mesh.Elements()
    edges = [mesh.Nodes(:,elem(1,:))'; mesh.Nodes(:,elem(2,:))'; mesh.Nodes(:,elem(3,:))'];
    
    % Calculate centroids
    x_cent = sum(edges(:,1)) / 3;
    y_cent = sum(edges(:,2)) / 3;
    
    centroids = [centroids; [x_cent, y_cent]];
end

%return;

% TODO: Remove from production

% Plot overlayed pde and scatter
figure
hold on
pdeplot(pdem, 'ElementLabels','on');
scatter(centroids(:,1), centroids(:,2));
title('Centre points of each element');
hold off

% Plot pde
figure
pdeplot(pdem);
title('Generated mesh');

% Centre points
figure
scatter(centroids(:,1), centroids(:,2));
title('Centre points of elements');
